package br.org.kinflasy.apis.churches.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.clients.PeopleFilterClient;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.builder.implementations.ConditionBuilder;
import br.org.kinflasy.libs.people_filters.dto.PeopleFilterTestRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChurchSecurityService {

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

    public boolean isIntegrantOfSomaInChurch(final UUID churchId, final IntegrationType integrationType,
            final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .isIntegrantOfExtensionInChurch(churchId, Extension.SOMA, integrationType)
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

    public boolean isIntegrantOfSomaInUnit(final UUID unitId, final IntegrationType integrationType,
            final PersonDto person) {
        final var condition = ConditionBuilder
                .thePerson()
                .isIntegrantOfExtensionInUnit(unitId, Extension.SOMA, integrationType)
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        return client.test(request);
    }

}
