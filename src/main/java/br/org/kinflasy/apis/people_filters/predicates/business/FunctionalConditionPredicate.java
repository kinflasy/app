package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.FunctionalCondition;

@Component
public class FunctionalConditionPredicate implements ConditionPredicate<FunctionalCondition> {

    @Override
    public boolean test(final FunctionalCondition condition, final PersonDto person) {
        return condition.getPredicate().test(person);
    }

}
