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

import br.org.kinflasy.apis.churches.converters.department.DepartmentConverter;
import br.org.kinflasy.apis.churches.entities.department.Department;
import br.org.kinflasy.apis.churches.entities.department.ExtensionSubscription;
import br.org.kinflasy.apis.churches.repositories.department.DepartmentRepository;
import br.org.kinflasy.apis.churches.repositories.department.ExtensionSubscriptionRepository;
import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.dto.access_rules.CharacteristicRule;
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
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientReadRequest;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.configuration.ClientWriteTuplesOptions;
import dev.openfga.sdk.api.model.WriteRequestWrites.OnDuplicateEnum;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class DepartmentService {

    private static final String NOT_FOUND_MESSAGE = "Departamento não encontrado";

    private static final String TYPE_DEPARTMENT = "department:";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final DepartmentConverter converter;
    private final DepartmentRepository repository;
    private final ExtensionSubscriptionRepository subscriptionRepository;

    private final IntegrationService integrationService;

    private final OpenFgaClient fgaClient;

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

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id)")
    public DepartmentDto create(final UUID unitId, final DepartmentRequest.WithRules request) {
        // Construir departamento
        final var department = converter.toEntity(request);
        // department.setVisibilityId(visibility.getId());

        // Associar unidade
        department.setUnitId(unitId);

        // Salvar
        final var created = repository.saveAndFlush(department);

        // Salvar regras de visibilidade e ingresso
        writeVisibilityRules(created, request.getVisibilityRules());
        writeJoinRules(created, request.getJoinRules());

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

    @PreAuthorize("@fga.check('department', #id, 'can_edit', 'user', principal.id)")
    public DepartmentDto update(final UUID id, final DepartmentRequest request) {
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var modified = converter.toEntity(request, original);

        repository.save(modified);
        return converter.toDto(modified);
    }

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

    @PreAuthorize("@fga.check('department', #id, 'can_manage', 'user', principal.id)")
    public void dissociateExtension(final UUID id, final ExtensionSubscriptionRequest request) {
        subscriptionRepository.findByDepartmentIdAndExtension(id, request.getExtension())
                .ifPresent(subscriptionRepository::delete);
    }

    @SneakyThrows
    @PreAuthorize("@fga.check('department', #id, 'can_manage', 'user', principal.id)")
    public List<AccessRule> listVisibilityRules(final UUID id) {
        final var request = new ClientReadRequest()
                ._object(TYPE_DEPARTMENT + id)
                .relation("can_view");

        return listRules(request);
    }

    @SneakyThrows
    @PreAuthorize("@fga.check('department', #id, 'can_manage', 'user', principal.id)")
    public List<AccessRule> listJoinRules(final UUID id) {
        final var request = new ClientReadRequest()
                ._object(TYPE_DEPARTMENT + id)
                .relation("can_join");

        return listRules(request);
    }

    /*
     * VERIFICAÇÃO DE ACESSO REDIRECIONADA
     */

    public IntegrationDto addIntegrant(final UUID id, final IntegrationRequest request) {
        return repository.findById(id)
                .map(ignoredDepartment -> integrationService.create(id, request))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public Pending askToJoin(final UUID id) {
        return repository.findById(id)
                .map(department -> integrationService.askToJoin(id, department.getUnitId()))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    /*
     * MÉTODOS PRIVADOS
     */

    private List<AccessRule> getDefaultVisibilityRules(final Department department) {
        return List.of(new UnitRule(department.getUnitId(), Affiliation.CONGREGATED));
    }

    private List<AccessRule> getDefaultJoinRules(final Department department) {
        return List.of(new UnitRule(department.getUnitId(), Affiliation.CONGREGATED));
    }

    @SneakyThrows
    private void writeVisibilityRules(final Department department, final Collection<AccessRule> rules) {
        // Usar padrão caso não venham
        final var chosenRules = rules.isEmpty() ? getDefaultVisibilityRules(department) : rules;

        // Gerar tuplas
        final var tuples = chosenRules.stream()
                .map(rule -> new ClientTupleKey()
                        ._object(TYPE_DEPARTMENT + department.getId())
                        .relation("can_view")
                        .user(rule.getFgaUser())
                        .condition(rule.getFgaCondition()))
                .toList();

        // Salvar
        fgaClient.writeTuples(tuples, new ClientWriteTuplesOptions().onDuplicate(OnDuplicateEnum.IGNORE))
                .join();
    }

    @SneakyThrows
    private void writeJoinRules(final Department department, final Collection<AccessRule> rules) {
        // Usar padrão caso não venham
        final var chosenRules = rules.isEmpty() ? getDefaultJoinRules(department) : rules;

        // Gerar tuplas
        final var tuples = chosenRules.stream()
                .map(rule -> new ClientTupleKey()
                        ._object(TYPE_DEPARTMENT + department.getId())
                        .relation("can_join")
                        .user(rule.getFgaUser())
                        .condition(rule.getFgaCondition()))
                .toList();

        // Salvar
        fgaClient.writeTuples(tuples, new ClientWriteTuplesOptions().onDuplicate(OnDuplicateEnum.IGNORE)).join();
    }

    private List<AccessRule> listRules(final ClientReadRequest request) throws FgaInvalidParameterException {
        final var response = fgaClient.read(request).join();

        return response.getTuples().stream()
                .map(tuple -> {
                    final var user = tuple.getKey().getUser();
                    final var userParts = user.split(":");
                    final var userType = userParts[0];
                    final var userId = userParts[1];

                    final var characteristics = CharacteristicRule.of(tuple.getKey().getCondition());

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

}
