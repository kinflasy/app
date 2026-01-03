package br.org.kinflasy.apis.churches.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.entities.Membership;
import br.org.kinflasy.apis.churches.entities.department.Department;
import br.org.kinflasy.apis.churches.entities.department.ExtensionSubscription;
import br.org.kinflasy.apis.churches.entities.department.Integration;
import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.apis.churches.repositories.department.DepartmentRepository;
import br.org.kinflasy.apis.churches.repositories.department.ExtensionSubscriptionRepository;
import br.org.kinflasy.apis.churches.repositories.department.IntegrationRepository;
import br.org.kinflasy.clients.PeopleFilterClient;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.churches.dto.ChurchDto;
import br.org.kinflasy.libs.churches.dto.ChurchRequest;
import br.org.kinflasy.libs.churches.enums.UnitType;
import br.org.kinflasy.libs.churches.enums.department.DepartmentType;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChurchUseCaseService {

    private AuthUtils authUtils;
    private ModelMapper mapper;

    private MembershipRepository membershipRepository;
    private DepartmentRepository departmentRepository;
    private IntegrationRepository integrationRepository;
    private ExtensionSubscriptionRepository subscriptionRepository;

    private ChurchService churchService;
    private UnitService unitService;

    private PeopleFilterClient peopleFilterClient;

    public ChurchDto.Starter createStarter(final ChurchRequest.Starter request) {
        // Criar igreja
        final var church = churchService.create(request);

        // Criar unidade sede
        request.getUnit().setType(UnitType.MAIN);
        final var unit = unitService.create(church.getId(), request.getUnit());

        // Obter usuário logado
        final var loggedUser = authUtils.getLoggedUser();

        // Adicionar usuário logado como membro da unidade Sede
        final var membership = new Membership();
        membership.setUnitId(unit.getId());
        membership.setPersonId(loggedUser.getId());
        membership.setAffiliation(Affiliation.MEMBER);
        membershipRepository.save(membership);

        // Buscar/criar filtros de pessoas
        final var everybody = peopleFilterClient
                .findOrCreate(thePerson -> thePerson.is(PersonCharacteristic.EVERYBODY));
        final var congregatedOfChurch = peopleFilterClient
                .findOrCreate(thePerson -> thePerson.isMemberOfChurch(church.getId(), Affiliation.CONGREGATED));

        // Criar ministério pastoral
        final var pastorate = new Department();
        pastorate.setUnitId(unit.getId());
        pastorate.setName("Ministério Pastoral");
        pastorate.setSlug("pastoral");
        pastorate.setType(DepartmentType.ADMINISTRATIVE);
        pastorate.setVisibilityId(everybody.getId());
        departmentRepository.save(pastorate);

        // Criar secretaria
        final var secretariat = new Department();
        secretariat.setUnitId(unit.getId());
        secretariat.setName("Secretaria");
        secretariat.setSlug("secretaria");
        secretariat.setType(DepartmentType.ADMINISTRATIVE);
        secretariat.setVisibilityId(congregatedOfChurch.getId());
        final var createdSecretariat = departmentRepository.save(secretariat);

        // Associar extensão SOMA à secretaria
        final var soma = new ExtensionSubscription();
        soma.setDepartmentId(createdSecretariat.getId());
        soma.setExtension(Extension.SOMA);
        subscriptionRepository.save(soma);

        // Adicionar usuário logado à secretaria
        final var integration = new Integration();
        integration.setDepartmentId(createdSecretariat.getId());
        integration.setMembershipId(loggedUser.getId());
        integration.setType(IntegrationType.LEADER);
        integrationRepository.save(integration);

        return mapper.map(church, ChurchDto.Starter.class)
                .setUnit(unit);
    }

}
