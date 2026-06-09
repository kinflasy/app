package br.org.kinflasy.apis.calendar.services.scales;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.calendar.entities.scales.CollaboratorScale;
import br.org.kinflasy.apis.calendar.entities.scales.OwnerScale;
import br.org.kinflasy.apis.calendar.entities.scales.Scale;
import br.org.kinflasy.apis.calendar.entities.scales.ScaleItem;
import br.org.kinflasy.apis.calendar.repositories.EventCollaborationRepository;
import br.org.kinflasy.apis.calendar.repositories.scales.ScaleItemRepository;
import br.org.kinflasy.apis.calendar.repositories.scales.ScaleRepository;
import br.org.kinflasy.apis.calendar.services.CalendarEventService;
import br.org.kinflasy.apis.calendar.services.DepartmentCalendarEventService;
import br.org.kinflasy.apis.calendar.services.UnitCalendarEventService;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.calendar.dto.scales.CollaboratorScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.OwnerScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleItemDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleItemRequest;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScaleService {

    private final ModelMapper mapper;
    private final AuthUtils authUtils;

    private final ScaleRepository repository;
    private final ScaleItemRepository itemRepository;
    private final EventCollaborationRepository collaborationRepository;

    private final OwnerScaleService ownerScaleService;
    private final CalendarEventService calendarEventService;
    private final CollaboratorScaleService collaboratorScaleService;
    private final UnitCalendarEventService unitCalendarEventService;
    private final DepartmentCalendarEventService departmentCalendarEventService;

    private final ApplicationEventPublisher publisher;

    /*
     * ACESSO AUTENTICADO
     */

    @PreAuthorize("isAuthenticated()")
    public List<ScaleDto.DetailingCalendarEvent> listByLoggedUserInRange(final LocalDateTime start,
            final LocalDateTime end) {
        final var loggedUser = authUtils.getLoggedUser();
        return listByPersonInRange(loggedUser.getId(), start, end);
    }

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('department', #departmentId, 'can_observe', 'user', principal.id)")
    public List<ScaleDto.DetailingCalendarEvent> listByDepartmentInRange(final UUID departmentId,
            final LocalDateTime start, final LocalDateTime end) {
        // Obter escalas cujo dono é este departamento
        final var ownerScales = departmentCalendarEventService.listInRange(departmentId, start, end).stream()
                .flatMap(event -> ownerScaleService.listByCalendarEventId(event.getId()).stream()
                        .map(scale -> mapper.map(scale, ScaleDto.DetailingCalendarEvent.class)
                                .setCalendarEvent(event)));

        // Obter escalas de colaborações associadas a este departamento
        final var collabScales = calendarEventService.listCollaborationsInRange(departmentId, start, end).stream()
                .flatMap(event -> collaboratorScaleService
                        .listByCalendarEventIdAndDepartmentId(event.getId(), departmentId).stream()
                        .map(scale -> mapper.map(scale, ScaleDto.DetailingCalendarEvent.class)
                                .setCalendarEvent(event)));

        return Stream.concat(ownerScales, collabScales)
                .distinct()
                .sorted((a, b) -> a.getCalendarEvent().getStartDateTime()
                        .compareTo(b.getCalendarEvent().getStartDateTime()))
                .toList();
    }

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id) or "
            + "@fga.check('unit', #unitId, 'department_manager', 'user', principal.id)")
    public List<ScaleDto.DetailingCalendarEvent> listByUnitInRange(final UUID unitId, final LocalDateTime start,
            final LocalDateTime end) {
        // Obter escalas cuja dona é esta unidade
        return unitCalendarEventService.listInRange(unitId, start, end).stream()
                .flatMap(event -> ownerScaleService.listByCalendarEventId(event.getId()).stream()
                        .map(scale -> mapper.map(scale, ScaleDto.DetailingCalendarEvent.class)
                                .setCalendarEvent(event)))
                .distinct()
                .sorted((a, b) -> a.getCalendarEvent().getStartDateTime()
                        .compareTo(b.getCalendarEvent().getStartDateTime()))
                .toList();
    }

    @PreAuthorize("@fga.check('user', #personId, 'admin', 'user', principal.id) or "
            + "#personId.equals(principal.id)")
    @PostFilter("@fga.check('scale', filterObject.id, 'can_view', 'user', principal.id) and "
            + "@fgau.withCharacteristics('calendar_event', filterObject.calendarEvent.id, 'can_view')")
    public List<ScaleDto.DetailingCalendarEvent> listByPersonInRange(final UUID personId, final LocalDateTime start,
            final LocalDateTime end) {
        // Obter escalações da pessoa
        final var ownerScaleItems = itemRepository.findOwnerScalesByPersonIdInRange(personId, start, end).stream();
        final var collaboratorScaleItems = itemRepository.findCollaboratorScalesByPersonIdInRange(personId, start, end)
                .stream();

        return Stream.concat(ownerScaleItems, collaboratorScaleItems)
                .distinct()

                // Detalhar escala
                .map(item -> repository.findById(item.getScaleId())
                        .flatMap(scale -> {

                            // Detalhar evento associado à escala
                            final var event = switch (scale) {
                                case OwnerScale ownerScale ->
                                    calendarEventService.findById(ownerScale.getCalendarEventId());
                                case CollaboratorScale collaboratorScale ->
                                    collaborationRepository.findById(collaboratorScale.getCollaborationId())
                                            .flatMap(collab -> calendarEventService
                                                    .findById(collab.getCalendarEventId()));
                                default -> throw new IllegalStateException(
                                        "Tipo desconhecido de escala: " + scale.getClass().getName());
                            };

                            return event.map(evt -> mapper.map(scale, ScaleDto.DetailingCalendarEvent.class)
                                    .setCalendarEvent(evt));
                        }))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted((a, b) -> a.getCalendarEvent().getStartDateTime()
                        .compareTo(b.getCalendarEvent().getStartDateTime()))
                .toList();
    }

    @PreAuthorize("@fga.check('scale', #id, 'can_view', 'user', principal.id)")
    public Optional<ScaleDto> findById(final UUID id) {
        return repository.findById(id)
                .map(this::toDto);
    }

    @PreAuthorize("@fga.check('scale', #id, 'can_edit', 'user', principal.id)")
    public Optional<ScaleDto> update(final UUID id, final ScaleRequest request) {
        return repository.findById(id)
                .map(scale -> {
                    // Guardar estado original
                    final var original = toDto(scale);

                    // Atualizar e salvar
                    scale.setLineupId(request.getLineupId());
                    final var modified = toDto(repository.save(scale));

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Updated<>(original, modified));

                    return modified;
                });
    }

    @PreAuthorize("@fga.check('scale', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        repository.findById(id)
                .ifPresent(scale -> {
                    // Guardar estado original
                    final var original = toDto(scale);

                    // Deletar
                    repository.delete(scale);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(original));
                });
    }

    @PreAuthorize("@fga.check('scale', #id, 'can_view', 'user', principal.id)")
    public List<ScaleItemDto> listItems(final UUID id) {
        return itemRepository.findByScaleId(id).stream()
                .map(item -> mapper.map(item, ScaleItemDto.class))
                .toList();
    }

    @PreAuthorize("@fga.check('scale', #id, 'can_edit', 'user', principal.id)")
    public ScaleItemDto addItem(final UUID id, final ScaleItemRequest request) {
        return itemRepository.findByScaleIdAndPersonIdAndRoleId(id, request.getPersonId(), request.getRoleId())
                .map(entity -> mapper.map(entity, ScaleItemDto.class))
                .orElseGet(() -> {
                    // Construir entidade
                    final var entity = new ScaleItem();
                    entity.setScaleId(id);
                    entity.setPersonId(request.getPersonId());
                    entity.setRoleId(request.getRoleId());

                    // Salvar e converter para DTO
                    final var saved = itemRepository.save(entity);
                    final var dto = mapper.map(saved, ScaleItemDto.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Created<>(dto));

                    return dto;
                });
    }

    @PreAuthorize("@fga.check('scale', #id, 'can_edit', 'user', principal.id)")
    public void removeItem(final UUID id, final ScaleItemRequest request) {
        itemRepository.findByScaleIdAndPersonIdAndRoleId(id, request.getPersonId(), request.getRoleId())
                .ifPresent(item -> {
                    // Guardar estado original
                    final var original = mapper.map(item, ScaleItemDto.class);

                    // Deletar
                    itemRepository.delete(item);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(original));
                });
    }

    /*
     * ACESSO PRIVADO
     */

    private ScaleDto toDto(final Scale scale) {
        return switch (scale) {
            case OwnerScale ownerScale -> new OwnerScaleDto()
                    .setCalendarEventId(ownerScale.getCalendarEventId())
                    .setId(ownerScale.getId())
                    .setLineupId(ownerScale.getLineupId());
            case CollaboratorScale collaboratorScale -> new CollaboratorScaleDto()
                    .setCollaborationId(collaboratorScale.getCollaborationId())
                    .setId(collaboratorScale.getId())
                    .setLineupId(collaboratorScale.getLineupId());
            default ->
                throw new IllegalStateException("Tipo desconhecido de escala: " + scale.getClass().getName());
        };
    }

}
