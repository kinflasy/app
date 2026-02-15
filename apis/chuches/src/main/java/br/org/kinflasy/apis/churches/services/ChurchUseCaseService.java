package br.org.kinflasy.apis.churches.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.config.ChurchesFgaTupleManager;
import br.org.kinflasy.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.churches.dto.ChurchDto;
import br.org.kinflasy.libs.churches.dto.ChurchRequest;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionRequest;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationRequest;
import br.org.kinflasy.libs.churches.enums.UnitType;
import br.org.kinflasy.libs.churches.enums.department.DepartmentType;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.client.model.ClientWriteRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Service
@AllArgsConstructor
public class ChurchUseCaseService {

    private AuthUtils authUtils;
    private ModelMapper mapper;

    private ChurchService churchService;
    private UnitService unitService;
    private DepartmentService departmentService;

    private OpenFgaClient fgaClient;
    private ChurchesFgaTupleManager tupleManager;

    /*
     * ACESSO LOGADO
     */

    @SneakyThrows
    @PreAuthorize("isAuthenticated()")
    public ChurchDto.Starter createStarter(final ChurchRequest.Starter request) {
        // Obter usuário logado
        final var loggedUser = authUtils.getLoggedUser();

        // Criar igreja
        final var church = churchService.create(request);

        // Autorizar usuário como registrante da igreja
        final var creatorTuple = new ClientTupleKey()
                .user("user:" + loggedUser.getId())
                .relation("creator")
                ._object("church:" + church.getId());

        fgaClient.write(new ClientWriteRequest().writes(List.of(creatorTuple))).join();

        // Criar unidade sede
        request.getUnit().setType(UnitType.MAIN);
        final var unit = unitService.create(church.getId(), request.getUnit());

        tupleManager.handleUnitCreated(new EntityEvent.Created<>(unit)).join();

        // Adicionar usuário logado como membro da unidade Sede
        final var membership = unitService.addMember(unit.getId(), new MembershipRequest()
                .setPersonId(loggedUser.getId()).setAffiliation(Affiliation.MEMBER));

        tupleManager.handleMembershipCreated(new EntityEvent.Created<>(membership)).join();

        // Criar ministério pastoral
        final var pastorate = unitService.createDepartment(unit.getId(), new DepartmentRequest()
                .setName("Ministério Pastoral").setSlug("pastoral")
                .setType(DepartmentType.ADMINISTRATIVE));

        // Criar secretaria
        final var secretariat = unitService.createDepartment(unit.getId(), new DepartmentRequest()
                .setName("Secretaria").setSlug("secretaria")
                .setType(DepartmentType.ADMINISTRATIVE));

        // Relacionar departamentos com unidade no FGA
        tupleManager.handleDepartmentCreated(new EntityEvent.Created<>(pastorate)).join();
        tupleManager.handleDepartmentCreated(new EntityEvent.Created<>(secretariat)).join();

        // Adicionar usuário logado à secretaria
        final var secretary = departmentService.addIntegrant(secretariat.getId(), new IntegrationRequest()
                .setMembershipId(membership.getId())
                .setType(IntegrationType.LEADER));

        tupleManager.handleIntegrationCreated(new EntityEvent.Created<>(secretary)).join();

        // Associar extensão SOMA à secretaria
        departmentService.subscribeToExtension(secretariat.getId(),
                new ExtensionSubscriptionRequest().setExtension(Extension.SOMA));

        fgaClient.deleteTuples(List.of(creatorTuple));

        return mapper.map(church, ChurchDto.Starter.class)
                .setUnit(unit);
    }

}
