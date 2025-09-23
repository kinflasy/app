package br.org.kinflasy.clients.local;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.controllers.PeopleFilterController;
import br.org.kinflasy.clients.PeopleFilterClient;
import br.org.kinflasy.libs.people_filters.dto.PeopleFilterTestRequest;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PeopleFilterLocalClient implements PeopleFilterClient {

    private final PeopleFilterController controller;

    @Override
    public boolean test(final PeopleFilterTestRequest request) {
        return Optional.ofNullable(controller.test(request).getBody())
                .orElse(false);
    }

}
