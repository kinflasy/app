package br.org.kinflasy.apis.people_filters.predicates.structure;

import org.springframework.stereotype.Component;

import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.base_conditions.conditions.structure.ConditionGroup;
import lombok.Value;

@Component
public interface ConditionPredicateGroup<C extends ConditionGroup> extends ConditionPredicate<C> {

    @Value
    class Tuple<C extends Condition> {
        private final C condition;
        private final ConditionPredicate<C> predicate;
    }

}
