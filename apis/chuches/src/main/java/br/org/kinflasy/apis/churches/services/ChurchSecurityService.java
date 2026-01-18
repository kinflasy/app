package br.org.kinflasy.apis.churches.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.clients.PeopleFilterClient;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.builder.implementations.ConditionBuilder;
import br.org.kinflasy.libs.people_filters.dto.PeopleFilterTestRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ChurchSecurityService {

    private final ChurchService churchService;

    private final PeopleFilterClient client;

    public boolean isMemberOfChurch(final UUID churchId, final Affiliation affiliation, final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .isMemberOfChurch(churchId, affiliation)
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

    public boolean isMemberOfUnit(final UUID unitId, final Affiliation affiliation, final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .isMemberOfUnit(unitId, affiliation)
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

    public boolean isIntegrantOfDepartment(final UUID departmentId, final IntegrationType integrationType,
            final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .isIntegrantOfDepartment(departmentId, integrationType)
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

    public boolean isIntegrantOfSomaInChurch(final UUID churchId, final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .isIntegrantOfExtensionInChurch(churchId, Extension.SOMA, null)
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

    public boolean isIntegrantOfSomaInUnit(final UUID unitId, final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .isIntegrantOfExtensionInUnit(unitId, Extension.SOMA, null)
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

    public boolean isLeaderOfSomaInChurch(final UUID churchId, final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .isIntegrantOfExtensionInChurch(churchId, Extension.SOMA, IntegrationType.LEADER)
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

    public boolean isLeaderOfSomaInUnit(final UUID unitId, final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .isIntegrantOfExtensionInUnit(unitId, Extension.SOMA, IntegrationType.LEADER)
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

    public boolean matchesCondition(final UUID storedConditionId, final PersonDto person) {
        return client.test(storedConditionId, person);
    }

    public boolean canCreateUnit(final UUID churchId, final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .matchesAnyCondition(thePerson -> thePerson

                        // Ou a Igreja e a unidade estão sendo criadas agora
                        .matches(ignoredPerson -> {
                            return churchService.findById(churchId)

                                    // Se a Igreja estiver persistida, certificar-se de que ela está sem unidades
                                    .map(ignoredChurch -> {
                                        return churchService.listUnits(churchId).isEmpty();
                                    })

                                    // Se não for encontrada, permitir o acesso
                                    .orElse(true);
                        })

                        // Ou o usuário é integrante de um departamento SOMA
                        .isIntegrantOfExtensionInChurch(churchId, Extension.SOMA, null))
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

}
