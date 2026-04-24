package br.org.kinflasy.apis.churches.services.department;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.entities.department.Integration;
import br.org.kinflasy.apis.churches.entities.department.PendingIntegration;
import br.org.kinflasy.apis.churches.repositories.department.IntegrationRepository;
import br.org.kinflasy.apis.churches.repositories.department.PendingIntegrationRepository;
import br.org.kinflasy.apis.churches.services.MembershipService;
import br.org.kinflasy.apis.churches.services.UnitService;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationRequest;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto.Pending;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class IntegrationService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;
    private final AuthUtils authUtils;

    private final IntegrationRepository repository;
    private final PendingIntegrationRepository pendingRepository;

    private final UnitService unitService;
    private final DepartmentService departmentService;
    private final MembershipService membershipService;

    @PreAuthorize("@fga.check('department', #departmentId, 'can_observe', 'user', principal.id)")
    public List<IntegrationDto.Detailed> listByDepartment(final UUID departmentId) {
        // Consultar departamento
        return departmentService.findById(departmentId)

                // Listar suas integrações
                .map(department -> repository.findByDepartmentId(departmentId).stream()

                        // Para cada integração, detalhar a membership associada
                        .map(integration -> membershipService.findById(integration.getMembershipId())

                                // Gerar DTO detalhado
                                .map(membership -> new IntegrationDto.Detailed()
                                        .setId(integration.getId())
                                        .setDepartment(department)
                                        .setMembership(membership)
                                        .setType(integration.getType())))

                        // Filtrar integrações com membership inexistente (embora isso não devesse
                        // acontecer)
                        .filter(Optional::isPresent)

                        // Coletar resultados
                        .map(Optional::get)
                        .toList())

                // Se departamento não existir, retornar lista vazia
                .orElseGet(Collections::emptyList);
    }

    @PreAuthorize("@fga.check('membership', #membershipId, 'can_view', 'user', principal.id)")
    public List<IntegrationDto> listByMembership(final UUID membershipId) {
        return repository.findByMembershipId(membershipId).stream()
                .map(integration -> mapper.map(integration, IntegrationDto.class))
                .toList();
    }

    @PreAuthorize("@fga.check('department', #departmentId, 'can_manage', 'user', principal.id) and "
            + "@fgau.withCharacteristics('department', #departmentId, 'can_join', 'membership', #request.membershipId + '#user', "
            + "@membershipService.findById(#request.membershipId).get().person)")
    public IntegrationDto create(final UUID departmentId, final IntegrationRequest request) {
        // Construir entidade
        final var entity = mapper.map(request, Integration.class);
        entity.setId(null);
        entity.setDepartmentId(departmentId);

        // Salvar
        final var saved = repository.save(entity);

        // Gerar DTO
        final var dto = mapper.map(saved, IntegrationDto.class);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

    @PreAuthorize("@fga.check('department', #departmentId, 'can_observe', 'user', principal.id)")
    public Optional<IntegrationDto> findByDepartmentAndMembership(final UUID departmentId, final UUID membershipId) {
        return repository.findByDepartmentIdAndMembershipId(departmentId, membershipId).stream()
                .findFirst()
                .map(integration -> mapper.map(integration, IntegrationDto.class));
    }

    @PreAuthorize("@fga.check('department', #departmentId, 'can_manage', 'user', principal.id) or @fga.check('membership', #membershipId, 'can_view', 'user', principal.id)")
    public void deleteByDepartmentAndMembership(final UUID departmentId, final UUID membershipId) {
        repository.findByDepartmentIdAndMembershipId(departmentId, membershipId).stream()
                .findFirst().ifPresent(repository::delete);
    }

    @PreAuthorize("@fgau.withCharacteristics('department', #departmentId, 'can_join')")
    public Pending askToJoin(final UUID departmentId, final UUID unitId) {
        final var loggedUser = authUtils.getLoggedUser();

        return unitService.findActiveMembership(unitId, loggedUser.getId())
                .map(membership -> {
                    final var entity = new PendingIntegration();
                    entity.setDepartmentId(departmentId);
                    entity.setMembershipId(membership.getId());

                    final var saved = pendingRepository.save(entity);

                    return mapper.map(saved, Pending.class);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        "Você só pode ingressar nos departamentos das unidades das quais você participa"));
    }

    @PreAuthorize("@fga.check('department', #departmentId, 'can_manage', 'user', principal.id)")
    public List<Pending> listPendingByDepartment(final UUID departmentId) {
        return pendingRepository.findByDepartmentId(departmentId).stream()
                .map(entity -> mapper.map(entity, Pending.class))
                .toList();
    }

    @PreAuthorize("@fga.check('membership', #membershipId, 'user', 'user', principal.id)")
    public List<Pending> listPendingByMembership(final UUID membershipId) {
        return pendingRepository.findByMembershipId(membershipId).stream()
                .map(entity -> mapper.map(entity, Pending.class))
                .toList();
    }

    @PreAuthorize("@fga.check('department', #departmentId, 'can_manage', 'user', principal.id) and "
            + "@fgau.withCharacteristics('department', #departmentId, 'can_join', 'membership', #membershipId + '#user', "
            + "@membershipService.findById(#request.membershipId).get().person))")
    public IntegrationDto confirmPending(final UUID departmentId, final UUID membershipId, final IntegrationType type) {
        return pendingRepository.findByDepartmentIdAndMembershipId(departmentId, membershipId)
                .map(pending -> {
                    // Construir integração oficial
                    final var request = new IntegrationRequest()
                            .setMembershipId(membershipId)
                            .setType(type);

                    // Salvar integração oficial
                    final var saved = create(departmentId, request);

                    // Excluir integração pendente
                    pendingRepository.delete(pending);

                    // Gerar DTO
                    return saved;
                })
                .orElseThrow(() -> new EntityNotFoundException("Essa solicitação não existe"));
    }

    @PreAuthorize("@fga.check('department', #departmentId, 'can_manage', 'user', principal.id) or "
            + " @fga.check('membership', #membershipId, 'user', 'user', principal.id)")
    public void deletePending(final UUID departmentId, final UUID membershipId) {
        pendingRepository.findByDepartmentIdAndMembershipId(departmentId, membershipId)

                // Excluir integração pendente
                .ifPresent(pendingRepository::delete);
    }

}
