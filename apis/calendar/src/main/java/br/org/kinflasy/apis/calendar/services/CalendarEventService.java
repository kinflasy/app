package br.org.kinflasy.apis.calendar.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.org.kinflasy.apis.calendar.clients.DepartmentClient;
import br.org.kinflasy.apis.calendar.clients.MediaClient;
import br.org.kinflasy.apis.calendar.entities.DepartmentCalendarEvent;
import br.org.kinflasy.apis.calendar.entities.EventCollaboration;
import br.org.kinflasy.apis.calendar.entities.UnitCalendarEvent;
import br.org.kinflasy.apis.calendar.repositories.CalendarEventRepository;
import br.org.kinflasy.apis.calendar.repositories.DepartmentCalendarEventRepository;
import br.org.kinflasy.apis.calendar.repositories.EventCollaborationRepository;
import br.org.kinflasy.apis.calendar.repositories.UnitCalendarEventRepository;
import br.org.kinflasy.apis.calendar.services.scales.CollaboratorScaleService;
import br.org.kinflasy.apis.calendar.services.scales.OwnerScaleService;
import br.org.kinflasy.libs.calendar.dto.CalendarEventDto;
import br.org.kinflasy.libs.calendar.dto.CalendarEventRequest;
import br.org.kinflasy.libs.calendar.dto.DepartmentCalendarEventDto;
import br.org.kinflasy.libs.calendar.dto.EventCollaborationDto;
import br.org.kinflasy.libs.calendar.dto.UnitCalendarEventDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleRequest;
import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.dto.access_rules.CharacteristicCondition;
import br.org.kinflasy.libs.churches.dto.access_rules.ChurchRule;
import br.org.kinflasy.libs.churches.dto.access_rules.DepartmentRule;
import br.org.kinflasy.libs.churches.dto.access_rules.UnitRule;
import br.org.kinflasy.libs.churches.dto.access_rules.UserRule;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.libs.media.validators.ProfileImageValidator;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientReadRequest;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.client.model.ClientTupleKeyWithoutCondition;
import dev.openfga.sdk.api.configuration.ClientWriteTuplesOptions;
import dev.openfga.sdk.api.model.WriteRequestWrites.OnDuplicateEnum;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Service
@AllArgsConstructor
public class CalendarEventService {

    private static final String TYPE_CALENDAR_EVENT = "calendar_event:";
    private static final String RELATION_CAN_VIEW = "can_view";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final CalendarEventRepository repository;
    private final UnitCalendarEventRepository unitEventRepository;
    private final DepartmentCalendarEventRepository departmentEventRepository;
    private final EventCollaborationRepository collaborationRepository;

    private final OwnerScaleService ownerScaleService;
    private final CollaboratorScaleService collaboratorScaleService;

    private final OpenFgaClient fgaClient;
    private final MediaClient mediaClient;
    private final DepartmentClient departmentClient;

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('department', #departmentId, 'can_observe', 'user', principal.id)")
    @PostFilter("@fgau.withCharacteristics('calendar_event', filterObject.id, 'can_view')")
    public List<CalendarEventDto> listCollaborationsInRange(final UUID departmentId, final LocalDateTime start,
            final LocalDateTime end) {
        return collaborationRepository.findByDepartmentIdInRange(departmentId, start, end).stream()
                .map(collaboration -> findById(collaboration.getCalendarEventId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()))
                .toList();
    }

    @PreAuthorize("@fgau.withCharacteristics('calendar_event', #id, 'can_view')")
    public Optional<CalendarEventDto> findById(final UUID id) {
        // Buscar o evento como sendo de unidade
        return unitEventRepository.findById(id)
                .map(entity -> (CalendarEventDto) mapper.map(entity, UnitCalendarEventDto.class))

                // Se não encontrar, buscar o evento como sendo de departamento
                .or(() -> departmentEventRepository.findById(id)
                        .map(entity -> mapper.map(entity, DepartmentCalendarEventDto.class)))

                // Para todos os casos, preencher as regras de visibilidade
                .map(dto -> dto.setVisibilityRules(listVisibilityRules(id)));
    }

    @Transactional
    @PreAuthorize("@fga.check('calendar_event', #id, 'can_edit', 'user', principal.id)")
    public CalendarEventDto update(final UUID id, final CalendarEventRequest request) {
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new IllegalArgumentException("A data de início deve ser antes da data do fim");
        }

        final var entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
        final var original = mapper.map(entity, CalendarEventDto.class);

        mapper.map(request, entity);
        final var saved = repository.save(entity);
        final var dto = mapper.map(saved, CalendarEventDto.class);

        publisher.publishEvent(new EntityEvent.Updated<>(original, dto));

        return dto;
    }

    @PreAuthorize("@fga.check('calendar_event', #id, 'can_edit', 'user', principal.id)")
    public Optional<CalendarEventDto> updateCardImage(final UUID id, final MultipartFile file) {
        return repository.findById(id)
                .map(calendarEvent -> {
                    // Validar a imagem
                    ProfileImageValidator.validate(file);

                    // Fazer upload da nova foto
                    final var uploaded = mediaClient.upload(file).getBody();

                    // Deletar a foto antiga, se existir
                    Optional.ofNullable(calendarEvent.getCardImageId())
                            .ifPresent(mediaClient::delete);

                    // Atualizar a referência da foto no banco de dados
                    calendarEvent.setCardImageId(uploaded.getId());
                    final var saved = repository.save(calendarEvent);

                    // Mapear a entidade atualizada para DTO
                    return mapper.map(saved, CalendarEventDto.class);
                });
    }

    @PreAuthorize("@fga.check('calendar_event', #id, 'can_edit', 'user', principal.id)")
    public Optional<CalendarEventDto> deleteCardImage(final UUID id) {
        return repository.findById(id)
                .map(calendarEvent -> {
                    // Deletar a foto antiga, se existir
                    Optional.ofNullable(calendarEvent.getCardImageId())
                            .ifPresent(mediaClient::delete);

                    // Remover a referência da foto no banco de dados
                    calendarEvent.setCardImageId(null);
                    final var saved = repository.save(calendarEvent);

                    // Mapear a entidade atualizada para DTO
                    return mapper.map(saved, CalendarEventDto.class);
                });
    }

    @Transactional
    @PreAuthorize("@fga.check('calendar_event', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        repository.findById(id)
                .ifPresent(entity -> {
                    // Deletar
                    repository.delete(entity);

                    // Publicar evento
                    switch (entity) {
                        case UnitCalendarEvent unitCalendarEvent:
                            publisher.publishEvent(new EntityEvent.Deleted<>(
                                    mapper.map(unitCalendarEvent, UnitCalendarEventDto.class)));
                            break;

                        case DepartmentCalendarEvent departmentCalendarEvent:
                            publisher.publishEvent(new EntityEvent.Deleted<>(
                                    mapper.map(departmentCalendarEvent, DepartmentCalendarEventDto.class)));
                            break;

                        default:
                            break;
                    }
                });
    }

    @PreAuthorize("@fgau.withCharacteristics('calendar_event', #id, 'can_view')")
    public List<AccessRule> listVisibilityRules(final UUID id) {
        return listRules(id, RELATION_CAN_VIEW);
    }

    @Transactional
    @PreAuthorize("@fga.check('calendar_event', #id, 'can_edit', 'user', principal.id)")
    public void replaceVisibilityRules(final UUID id, final Collection<AccessRule> rules) {
        replaceRules(id, RELATION_CAN_VIEW, rules);
    }

    @Transactional
    public void postCreate(final CalendarEventDto dto) {
        writeRules(dto.getId(), RELATION_CAN_VIEW, dto.getVisibilityRules());
    }

    @PreAuthorize("@fga.check('calendar_event', #id, 'can_manage', 'user', principal.id)")
    public List<DepartmentDto> listCollaborators(final UUID id) {
        return collaborationRepository.findByCalendarEventId(id).stream()
                .map(collaboration -> departmentClient.findById(collaboration.getDepartmentId()))
                .toList();
    }

    @Transactional
    @PreAuthorize("@fga.check('calendar_event', #id, 'can_manage', 'user', principal.id)")
    public Optional<EventCollaborationDto> addCollaborator(final UUID id, final UUID departmentId) {
        return repository.findById(id)
                .map(event -> {
                    // Construir a entidade
                    final var entity = new EventCollaboration();
                    entity.setCalendarEventId(id);
                    entity.setDepartmentId(departmentId);

                    // Salvar
                    final var saved = collaborationRepository.save(entity);
                    final var dto = mapper.map(saved, EventCollaborationDto.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Created<>(dto));

                    return dto;
                });
    }

    @Transactional
    @PreAuthorize("@fga.check('calendar_event', #id, 'can_manage', 'user', principal.id)")
    public void removeCollaborator(final UUID id, final UUID departmentId) {
        collaborationRepository.findByCalendarEventIdAndDepartmentId(id, departmentId)
                .ifPresent(collaboration -> {
                    // Deletar a colaboração
                    collaborationRepository.delete(collaboration);

                    // Publicar evento
                    final var dto = mapper.map(collaboration, EventCollaborationDto.class);
                    publisher.publishEvent(new EntityEvent.Deleted<>(dto));
                });
    }

    @PreAuthorize("@fga.check('calendar_event', #id, 'can_scale', 'user', principal.id)")
    public List<ScaleDto> listScales(final UUID id) {
        final var ownerScales = ownerScaleService.listByCalendarEventId(id);
        final var collaboratorScales = collaborationRepository.findByCalendarEventId(id).stream()
                .flatMap(collab -> collaboratorScaleService
                        .listByCalendarEventIdAndDepartmentId(id, collab.getDepartmentId())
                        .stream());

        return Stream.concat(ownerScales.stream(), collaboratorScales)
                .distinct()
                .toList();
    }

    @PreAuthorize("@fga.check('calendar_event', #id, 'can_manage', 'user', principal.id)")
    public ScaleDto createOwnerScale(final UUID id, final ScaleRequest request) {
        return ownerScaleService.create(id, request);
    }

    @PreAuthorize("@fga.check('calendar_event', #id, 'can_scale', 'user', principal.id) and "
            + " @fga.check('department', #departmentId, 'can_manage', 'user', principal.id) and "
            + " @fga.check('lineup', #request.lineupId, 'owner', 'department', #departmentId)")
    public Optional<ScaleDto> createCollaboratorScale(final UUID id, final UUID departmentId,
            final ScaleRequest request) {
        // Buscar a colaboração e criar a escala
        return collaboratorScaleService.create(id, departmentId, request)
                .map(ScaleDto.class::cast);
    }

    /*
     * MÉTODOS PRIVADOS
     */

    @SneakyThrows
    private List<AccessRule> listRules(final UUID id, final String relation) {
        final var request = new ClientReadRequest()
                ._object(TYPE_CALENDAR_EVENT + id)
                .relation(relation);

        final var response = fgaClient.read(request).join();

        return response.getTuples().stream()
                .map(tuple -> {
                    final var user = tuple.getKey().getUser();
                    final var userParts = user.split(":");
                    final var userType = userParts[0];
                    final var userId = userParts[1];

                    final var characteristics = CharacteristicCondition.of(tuple.getKey().getCondition());

                    return switch (userType) {
                        case "church" -> {
                            final var userIdParts = userId.split("#");
                            final var churchId = UUID.fromString(userIdParts[0]);
                            final var affiliation = Affiliation.valueOf(userIdParts[1].toUpperCase());
                            yield new ChurchRule(churchId, affiliation, characteristics);
                        }
                        case "unit" -> {
                            final var userIdParts = userId.split("#");
                            final var unitId = UUID.fromString(userIdParts[0]);
                            final var affiliation = Affiliation.valueOf(userIdParts[1].toUpperCase());
                            yield new UnitRule(unitId, affiliation, characteristics);
                        }
                        case "department" -> {
                            final var userIdParts = userId.split("#");
                            final var departmentId = UUID.fromString(userIdParts[0]);
                            final var integrationType = IntegrationType.valueOf(userIdParts[1].toUpperCase());
                            yield new DepartmentRule(departmentId, integrationType, characteristics);
                        }
                        case "user" -> new UserRule(userId, characteristics);
                        default -> throw new IllegalStateException("Tupla fora das regras");
                    };
                })
                .toList();
    }

    @SneakyThrows
    private void writeRules(final UUID id, final String relation, final Collection<AccessRule> rules) {
        // Gerar tuplas
        final var tuples = rules.stream()
                .map(rule -> {
                    final var tuple = new ClientTupleKey()
                            ._object(TYPE_CALENDAR_EVENT + id)
                            .relation(relation)
                            .user(rule.getFgaUser());

                    rule.getFgaCondition()
                            .ifPresent(tuple::condition);

                    return tuple;
                })
                .toList();

        if (!tuples.isEmpty()) {
            // Salvar
            fgaClient.writeTuples(tuples, new ClientWriteTuplesOptions().onDuplicate(OnDuplicateEnum.IGNORE)).join();
        }

    }

    @SneakyThrows
    private void clearRules(final UUID id, final String relation) {
        final var request = new ClientReadRequest()
                ._object(TYPE_CALENDAR_EVENT + id)
                .relation(relation);

        final var response = fgaClient.read(request).join();
        final var tuples = response.getTuples();

        if (!tuples.isEmpty()) {
            fgaClient.deleteTuples(tuples.stream()
                    .map(tuple -> {
                        final var key = tuple.getKey();
                        return new ClientTupleKeyWithoutCondition()
                                ._object(key.getObject())
                                .relation(key.getRelation())
                                .user(key.getUser());
                    })
                    .toList())
                    .join();
        }
    }

    private void replaceRules(final UUID id, final String relation, final Collection<AccessRule> rules) {
        clearRules(id, relation);
        writeRules(id, relation, rules);
    }

}
