package br.org.kinflasy.apis.churches.services.department;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.org.kinflasy.apis.churches.clients.MediaClient;
import br.org.kinflasy.apis.churches.converters.department.DepartmentConverter;
import br.org.kinflasy.apis.churches.entities.department.Department;
import br.org.kinflasy.apis.churches.entities.department.ExtensionSubscription;
import br.org.kinflasy.apis.churches.repositories.department.DepartmentRepository;
import br.org.kinflasy.apis.churches.repositories.department.ExtensionSubscriptionRepository;
import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.dto.access_rules.CharacteristicCondition;
import br.org.kinflasy.libs.churches.dto.access_rules.ChurchRule;
import br.org.kinflasy.libs.churches.dto.access_rules.UnitRule;
import br.org.kinflasy.libs.churches.dto.access_rules.UserRule;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionRequest;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto.Pending;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationRequest;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.churches.events.department.ExtensionEvent;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.libs.media.validators.ProfileImageValidator;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientReadRequest;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.client.model.ClientTupleKeyWithoutCondition;
import dev.openfga.sdk.api.configuration.ClientWriteTuplesOptions;
import dev.openfga.sdk.api.model.WriteRequestWrites.OnDuplicateEnum;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class DepartmentService {

    private static final String NOT_FOUND_MESSAGE = "Departamento não encontrado";

    private static final String TYPE_DEPARTMENT = "department:";
    private static final String RELATION_CAN_VIEW = "can_view";
    private static final String RELATION_CAN_JOIN = "can_join";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final DepartmentConverter converter;
    private final DepartmentRepository repository;
    private final ExtensionSubscriptionRepository subscriptionRepository;

    private final IntegrationService integrationService;

    private final OpenFgaClient fgaClient;
    private final MediaClient mediaClient;

    /*
     * ACESSO PÚBLICO
     */

    @PostFilter("@fgau.withCharacteristics('department', filterObject.id, 'can_view')")
    public List<DepartmentDto> listByUnitId(final UUID unitId) {
        return repository.findByUnitId(unitId).stream()
                .map(converter::toDto)
                .toList();
    }

    /*
     * ACESSO RESTRITO
     */

    @Transactional
    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id)")
    public DepartmentDto create(final UUID unitId, final DepartmentRequest.WithRules request) {
        // Construir departamento
        final var department = converter.toEntity(request);

        // Associar unidade
        department.setUnitId(unitId);

        // Salvar
        final var created = repository.saveAndFlush(department);

        // Salvar regras de visibilidade e ingresso (usar padrão, caso não venham)
        final var visibilityRules = request.getVisibilityRules().isEmpty()
                ? getDefaultVisibilityRules(department)
                : request.getVisibilityRules();
        final var joinRules = request.getJoinRules().isEmpty()
                ? getDefaultJoinRules(department)
                : request.getJoinRules();
        writeRules(created.getId(), RELATION_CAN_VIEW, visibilityRules);
        writeRules(created.getId(), RELATION_CAN_JOIN, joinRules);

        // Gerar DTO
        final var dto = converter.toDto(created);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

    @PreAuthorize("@fgau.withCharacteristics('department', #id, 'can_view')")
    public Optional<DepartmentDto> findById(final UUID id) {
        return repository.findById(id)
                .map(converter::toDto);
    }

    @Transactional
    @PreAuthorize("@fga.check('department', #id, 'can_edit', 'user', principal.id)")
    public DepartmentDto update(final UUID id, final DepartmentRequest request) {
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var modified = converter.toEntity(request, original);

        repository.save(modified);
        return converter.toDto(modified);
    }

    @PreAuthorize("@fga.check('department', #id, 'can_manage', 'user', principal.id)")
    public Optional<DepartmentDto> updateProfileImage(final UUID id, final MultipartFile file) {
        return repository.findById(id)
                .map(department -> {
                    // Validar a imagem
                    ProfileImageValidator.validate(file);

                    // Fazer upload da nova foto
                    final var uploaded = mediaClient.upload(file).getBody();

                    // Deletar a foto antiga, se existir
                    Optional.ofNullable(department.getProfileImageId())
                            .ifPresent(mediaClient::delete);

                    // Atualizar a referência da foto no banco de dados
                    department.setProfileImageId(uploaded.getId());
                    final var saved = repository.save(department);

                    // Mapear a entidade atualizada para DTO
                    return mapper.map(saved, DepartmentDto.class);
                });
    }

    @PreAuthorize("@fga.check('department', #id, 'can_manage', 'user', principal.id)")
    public Optional<DepartmentDto> deleteProfileImage(final UUID id) {
        return repository.findById(id)
                .map(department -> {
                    // Deletar a foto antiga, se existir
                    Optional.ofNullable(department.getProfileImageId())
                            .ifPresent(mediaClient::delete);

                    // Remover a referência da foto no banco de dados
                    department.setProfileImageId(null);
                    final var saved = repository.save(department);

                    // Mapear a entidade atualizada para DTO
                    return mapper.map(saved, DepartmentDto.class);
                });
    }

    @PreAuthorize("@fga.check('department', #id, 'can_manage', 'user', principal.id)")
    public Optional<DepartmentDto> updateCoverImage(final UUID id, final MultipartFile file) {
        return repository.findById(id)
                .map(department -> {
                    // Validar a imagem
                    ProfileImageValidator.validate(file);

                    // Fazer upload da nova foto
                    final var uploaded = mediaClient.upload(file).getBody();

                    // Deletar a foto antiga, se existir
                    Optional.ofNullable(department.getCoverImageId())
                            .ifPresent(mediaClient::delete);

                    // Atualizar a referência da foto no banco de dados
                    department.setCoverImageId(uploaded.getId());
                    final var saved = repository.save(department);

                    // Mapear a entidade atualizada para DTO
                    return mapper.map(saved, DepartmentDto.class);
                });
    }

    @PreAuthorize("@fga.check('department', #id, 'can_manage', 'user', principal.id)")
    public Optional<DepartmentDto> deleteCoverImage(final UUID id) {
        return repository.findById(id)
                .map(department -> {
                    // Deletar a foto antiga, se existir
                    Optional.ofNullable(department.getCoverImageId())
                            .ifPresent(mediaClient::delete);

                    // Remover a referência da foto no banco de dados
                    department.setCoverImageId(null);
                    final var saved = repository.save(department);

                    // Mapear a entidade atualizada para DTO
                    return mapper.map(saved, DepartmentDto.class);
                });
    }

    @Transactional
    @PreAuthorize("@fga.check('department', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        repository.deleteById(id);
    }

    @PreAuthorize("@fga.check('department', #id, 'can_observe', 'user', principal.id)")
    public List<Extension> listExtensions(final UUID id) {
        return subscriptionRepository.findByDepartmentId(id).stream()
                .map(ExtensionSubscription::getExtension)
                .toList();
    }

    @Transactional
    @PreAuthorize("@fga.check('department', #id, 'can_manage', 'user', principal.id)")
    public ExtensionSubscriptionDto subscribeToExtension(final UUID id, final ExtensionSubscriptionRequest request) {
        final var entity = mapper.map(request, ExtensionSubscription.class);
        entity.setDepartmentId(id);

        final var saved = subscriptionRepository.save(entity);

        // Gerar DTO
        final var dto = mapper.map(saved, ExtensionSubscriptionDto.class);

        // Publicar evento
        publisher.publishEvent(new ExtensionEvent.Subscribed<>(dto));

        return dto;
    }

    @PreAuthorize("@fga.check('department', #id, 'can_observe', 'user', principal.id)")
    public Optional<ExtensionSubscriptionDto> findExtension(final UUID id, final Extension extension) {
        return subscriptionRepository.findByDepartmentIdAndExtension(id, extension)
                .map(subscription -> mapper.map(subscription, ExtensionSubscriptionDto.class));
    }

    @Transactional
    @PreAuthorize("@fga.check('department', #id, 'can_manage', 'user', principal.id)")
    public void dissociateExtension(final UUID id, final ExtensionSubscriptionRequest request) {
        subscriptionRepository.findByDepartmentIdAndExtension(id, request.getExtension())
                .ifPresent(subscriptionRepository::delete);
    }

    @PreAuthorize("@fga.check('department', #id, 'can_observe', 'user', principal.id)")
    public List<AccessRule> listVisibilityRules(final UUID id) {
        return listRules(id, RELATION_CAN_VIEW);
    }

    @PreAuthorize("@fga.check('department', #id, 'can_observe', 'user', principal.id)")
    public List<AccessRule> listJoinRules(final UUID id) {
        return listRules(id, RELATION_CAN_JOIN);
    }

    @Transactional
    @PreAuthorize("@fga.check('department', #id, 'can_edit', 'user', principal.id)")
    public Optional<List<AccessRule>> resetVisibilityRules(final UUID id) {
        return resetRules(id, RELATION_CAN_VIEW);
    }

    @Transactional
    @PreAuthorize("@fga.check('department', #id, 'can_edit', 'user', principal.id)")
    public Optional<List<AccessRule>> resetJoinRules(final UUID id) {
        return resetRules(id, RELATION_CAN_JOIN);
    }

    @Transactional
    @PreAuthorize("@fga.check('department', #id, 'can_edit', 'user', principal.id)")
    public void replaceVisibilityRules(final UUID id, final Collection<AccessRule> rules) {
        replaceRules(id, RELATION_CAN_VIEW, rules);
    }

    @Transactional
    @PreAuthorize("@fga.check('department', #id, 'can_edit', 'user', principal.id)")
    public void replaceJoinRules(final UUID id, final Collection<AccessRule> rules) {
        replaceRules(id, RELATION_CAN_JOIN, rules);
    }

    /*
     * VERIFICAÇÃO DE ACESSO REDIRECIONADA
     */

    @Transactional
    public IntegrationDto addIntegrant(final UUID id, final IntegrationRequest request) {
        return repository.findById(id)
                .map(ignoredDepartment -> integrationService.create(id, request))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    public Pending askToJoin(final UUID id) {
        return repository.findById(id)
                .map(department -> integrationService.askToJoin(id, department.getUnitId()))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    /*
     * MÉTODOS PRIVADOS
     */

    private List<AccessRule> getDefaultVisibilityRules(final Department department) {
        return switch (department.getType()) {
            case ADMINISTRATIVE -> List.of(new UnitRule(department.getUnitId(), Affiliation.MEMBER));
            default -> List.of(new UnitRule(department.getUnitId(), Affiliation.CONGREGATED));
        };
    }

    private List<AccessRule> getDefaultJoinRules(final Department department) {
        return switch (department.getType()) {
            case ADMINISTRATIVE -> List.of(new UnitRule(department.getUnitId(), Affiliation.MEMBER));
            default -> List.of(new UnitRule(department.getUnitId(), Affiliation.CONGREGATED));
        };
    }

    @SneakyThrows
    private List<AccessRule> listRules(final UUID id, final String relation) {
        final var request = new ClientReadRequest()
                ._object(TYPE_DEPARTMENT + id)
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
                            ._object(TYPE_DEPARTMENT + id)
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
                ._object(TYPE_DEPARTMENT + id)
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

    private Optional<List<AccessRule>> resetRules(final UUID id, final String relation) {
        return repository.findById(id)
                .map(department -> {
                    final List<AccessRule> defaultRules = switch (relation) {
                        case RELATION_CAN_VIEW -> getDefaultVisibilityRules(department);
                        case RELATION_CAN_JOIN -> getDefaultJoinRules(department);
                        default -> throw new IllegalArgumentException("Relação não definida");
                    };

                    replaceRules(id, relation, defaultRules);

                    return defaultRules;
                });
    }

}
