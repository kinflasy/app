package br.org.kinflasy.clients;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import br.org.kinflasy.libs.people_filters.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.implementations.StarterConditionBuilder;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import br.org.kinflasy.libs.people_filters.dto.ConditionRequest;
import br.org.kinflasy.libs.people_filters.dto.PeopleFilterTestRequest;
import br.org.kinflasy.libs.people_filters.dto.StoredConditionDto;

@Component
public interface PeopleFilterClient {

    boolean test(PeopleFilterTestRequest request);

    StoredConditionDto<Condition> findOrCreate(ConditionRequest request);

    StoredConditionDto<Condition> findOrCreate(Function<StarterConditionBuilder, ReadyConditionBuilder> thePerson);

}
