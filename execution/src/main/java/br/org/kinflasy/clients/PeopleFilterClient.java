package br.org.kinflasy.clients;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.implementations.ConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.implementations.StarterConditionBuilder;
import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.base_conditions.dto.ConditionRequest;
import br.org.kinflasy.libs.people_filters.dto.PeopleFilterTestRequest;
import br.org.kinflasy.libs.base_conditions.dto.StoredConditionDto;

@Component
@FeignClient("people-filter-api")
public interface PeopleFilterClient {

    @PostMapping("test")
    boolean test(@RequestBody PeopleFilterTestRequest request);

    @GetMapping("{id}/test")
    boolean test(@PathVariable UUID id, @RequestBody PersonDto person);

    @PostMapping
    StoredConditionDto<Condition> findOrCreate(@RequestBody ConditionRequest request);

    default StoredConditionDto<Condition> findOrCreate(
            Function<StarterConditionBuilder, ReadyConditionBuilder> thePerson) {
        final var condition = thePerson.apply(ConditionBuilder.thePerson()).build();
        final var request = new ConditionRequest(condition);
        return findOrCreate(request);
    }

}
