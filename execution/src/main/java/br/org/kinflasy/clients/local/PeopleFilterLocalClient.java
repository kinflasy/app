package br.org.kinflasy.clients.local;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.controllers.PeopleFilterController;
import br.org.kinflasy.clients.PeopleFilterClient;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.implementations.ConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.implementations.StarterConditionBuilder;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import br.org.kinflasy.libs.people_filters.dto.ConditionRequest;
import br.org.kinflasy.libs.people_filters.dto.PeopleFilterTestRequest;
import br.org.kinflasy.libs.people_filters.dto.StoredConditionDto;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class PeopleFilterLocalClient implements PeopleFilterClient {

    private final PeopleFilterController controller;

    @Override
    public boolean test(final PeopleFilterTestRequest request) {
        return Optional.ofNullable(controller.test(request).getBody())
                .orElse(false);
    }

    @Override
    public boolean test(UUID id, PersonDto person) {
        return Optional.ofNullable(controller.test(id, person).getBody())
                .orElse(false);
    }

    public StoredConditionDto<Condition> findOrCreate(final ConditionRequest request) {
        return controller.findOrCreate(request).getBody();
    }

    @Override
    public StoredConditionDto<Condition> findOrCreate(
            Function<StarterConditionBuilder, ReadyConditionBuilder> thePerson) {
        final var condition = thePerson.apply(ConditionBuilder.thePerson()).build();
        final var request = new ConditionRequest(condition);
        return findOrCreate(request);
    }

}
