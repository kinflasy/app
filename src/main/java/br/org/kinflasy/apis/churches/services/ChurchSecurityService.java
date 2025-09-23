package br.org.kinflasy.apis.churches.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.clients.PeopleFilterClient;
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

    private final PeopleFilterClient client;

    public boolean isMemberOfChurch(final UUID churchId, final Affiliation affiliation, final PersonDto person) {
        log.info("ENTROU");
        final var condition = ConditionBuilder.thePerson()
                .not(thePerson -> thePerson
                        .isMemberOfChurch(churchId, affiliation))
                .build();

        final var request = new PeopleFilterTestRequest(condition, person);
        boolean result = client.test(request);

        log.info("Resultado: {}", result);

        return result;
    }

}
