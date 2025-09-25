package br.org.kinflasy.apis.churches.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.apis.churches.services.department.IntegrationService;
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
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChurchUseCaseService {

    private AuthUtils authUtils;
    private ModelMapper mapper;

    private ChurchService churchService;
    private UnitService unitService;
    private DepartmentService departmentService;
    private IntegrationService integrationService;

    public ChurchDto.Starter createStarter(final ChurchRequest.Starter request) {
        return authUtils.runAsSystem(() -> {
            // Criar igreja
            final var church = churchService.create(request);

            // Criar unidade sede
            request.getUnit().setType(UnitType.MAIN);
            final var unit = unitService.create(church.getId(), request.getUnit());

            // Adicionar usuário logado como membro da unidade Sede
            final var loggedUser = authUtils.getLoggedUser();
            final var membershipRequest = new MembershipRequest()
                    .setPersonId(loggedUser.getId())
                    .setAffiliation(Affiliation.MEMBER);
            unitService.addMember(unit.getId(), membershipRequest);

            // Criar ministério pastoral
            final var pastorateRequest = new DepartmentRequest()
                    .setName("Ministério Pastoral")
                    .setSlug("pastoral")
                    .setType(DepartmentType.ADMINISTRATIVE);
            departmentService.create(unit.getId(), pastorateRequest);

            // Criar secretaria
            final var secretariatRequest = new DepartmentRequest()
                    .setName("Secretaria")
                    .setSlug("secretaria")
                    .setType(DepartmentType.ADMINISTRATIVE);
            final var secretariat = departmentService.create(unit.getId(), secretariatRequest);

            // Associar extensão SOMA à secretaria
            final var somaRequest = new ExtensionSubscriptionRequest()
                    .setExtension(Extension.SOMA);
            departmentService.associateExtension(secretariat.getId(), somaRequest);

            // Adicionar usuário logado à secretaria
            final var integrationRequest = new IntegrationRequest()
                    .setPersonId(loggedUser.getId())
                    .setType(IntegrationType.LEADER);
            integrationService.create(secretariat.getId(), integrationRequest);

            return mapper.map(church, ChurchDto.Starter.class)
                    .setUnit(unit);
        });
    }

}
