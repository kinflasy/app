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
import br.org.kinflasy.libs.churches.dto.access_rules.UnitRule;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionRequest;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationRequest;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto.Pending;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.churches.events.department.ExtensionEvent;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.configuration.ClientWriteTuplesOptions;
import dev.openfga.sdk.api.model.WriteRequestWrites.OnDuplicateEnum;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class DepartmentService {

    private static final String NOT_FOUND_MESSAGE = "Departamento não encontrado";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final DepartmentConverter converter;
    private final DepartmentRepository repository;
    private final ExtensionSubscriptionRepository subscriptionRepository;

    private final IntegrationService integrationService;

    private final OpenFgaClient fgaClient;

    @PostFilter("@fgau.withCharacteristics('department', filterObject.id, 'can_view')")
    public List<DepartmentDto> listByUnitId(final UUID unitId) {
        return repository.findByUnitId(unitId).stream()
                .map(converter::toDto)
                .toList();
    }

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
        writeRules(created, request.getVisibilityRules(), request.getJoinRules());

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
    private void writeRules(final Department department, final Collection<AccessRule> visibilityRules,
            final Collection<AccessRule> joinRules) {
        /*
         * REGRAS DE VISIBILIDADE
         * ----------------------
         */

        // Usar padrão caso não venham
        final var chosenVisibilityRules = visibilityRules.isEmpty() ? getDefaultVisibilityRules(department)
                : visibilityRules;

        // Gerar tuplas
        final var visibilityTuples = chosenVisibilityRules.stream()
                .map(rule -> new ClientTupleKey()
                        ._object("department:" + department.getId())
                        .relation("can_view")
                        .user(rule.getFgaUser())
                        .condition(rule.getFgaCondition()))
                .toList();

        // Salvar
        fgaClient.writeTuples(visibilityTuples, new ClientWriteTuplesOptions().onDuplicate(OnDuplicateEnum.IGNORE))
                .join();

        /*
         * REGRAS DE INTEGRAÇÃO
         * --------------------
         */

        // Usar padrão caso não venham
        final var chosenJoinRules = joinRules.isEmpty() ? getDefaultJoinRules(department) : joinRules;

        // Gerar tuplas
        final var joinTuples = chosenJoinRules.stream()
                .map(rule -> new ClientTupleKey()
                        ._object("department:" + department.getId())
                        .relation("can_join")
                        .user(rule.getFgaUser())
                        .condition(rule.getFgaCondition()))
                .toList();

        // Salvar
        fgaClient.writeTuples(joinTuples, new ClientWriteTuplesOptions().onDuplicate(OnDuplicateEnum.IGNORE)).join();
    }

    @SneakyThrows
    private void listRules(final UUID departmentId) {
        final var users = fgaClient.listUsers(null).join();
        final var list = users.getUsers().stream()
                .map(user -> user.getUserset().getType())
                .toList();

        list.clear();
    }

}
