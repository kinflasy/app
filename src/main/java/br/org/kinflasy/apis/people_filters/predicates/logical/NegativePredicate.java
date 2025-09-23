package br.org.kinflasy.apis.people_filters.predicates.logical;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.factories.ConditionPredicateFactory;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.logical.NegativeCondition;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class NegativePredicate implements ConditionPredicate<NegativeCondition> {

    private ConditionPredicateFactory factory;

    @Override
    public boolean test(final NegativeCondition condition, final PersonDto person) {
        // Negar resultado da condição-base
        final var baseCondition = condition.getBaseCondition();
        return factory.getPredicate(baseCondition).negate().test(baseCondition, person);
    }

}
