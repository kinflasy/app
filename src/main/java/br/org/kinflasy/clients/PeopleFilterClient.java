package br.org.kinflasy.clients;

import org.springframework.stereotype.Component;

import br.org.kinflasy.libs.people_filters.dto.PeopleFilterTestRequest;

@Component
public interface PeopleFilterClient {

    boolean test(PeopleFilterTestRequest request);

}
