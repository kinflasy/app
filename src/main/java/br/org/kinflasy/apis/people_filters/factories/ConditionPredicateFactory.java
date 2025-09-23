package br.org.kinflasy.apis.people_filters.factories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.predicates.business.CharacteristicPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.ChurchMembershipPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.DepartmentIntegrationPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.IdentityPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.UnitMembershipPredicate;
import br.org.kinflasy.apis.people_filters.predicates.logical.AndPredicateGroup;
import br.org.kinflasy.apis.people_filters.predicates.logical.NegativePredicate;
import br.org.kinflasy.apis.people_filters.predicates.logical.OrPredicateGroup;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people_filters.conditions.business.CharacteristicCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ChurchMembershipCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.DepartmentIntegrationCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.IdentityCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.UnitMembershipCondition;
import br.org.kinflasy.libs.people_filters.conditions.logical.AndConditionGroup;
import br.org.kinflasy.libs.people_filters.conditions.logical.NegativeCondition;
import br.org.kinflasy.libs.people_filters.conditions.logical.OrConditionGroup;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.Value;

@Component
public class ConditionPredicateFactory {

    @Value
    private class ConditionEntry<C extends Condition> {
        private Class<C> conditionClass;
        private ConditionPredicate<C> predicate;
    }

    private final Set<ConditionEntry<? extends Condition>> entrySet = new HashSet<>();

    public ConditionPredicateFactory(final List<ConditionPredicate<?>> predicates) {
        // Associar condições lógicas a seus predicados
        entrySet.add(new ConditionEntry<>(AndConditionGroup.class,
                findPredicate(predicates, AndPredicateGroup.class)));
        entrySet.add(new ConditionEntry<>(OrConditionGroup.class,
                findPredicate(predicates, OrPredicateGroup.class)));
        entrySet.add(new ConditionEntry<>(NegativeCondition.class,
                findPredicate(predicates, NegativePredicate.class)));

        // Associar condições de negócio a seus predicados
        entrySet.add(new ConditionEntry<>(CharacteristicCondition.class,
                findPredicate(predicates, CharacteristicPredicate.class)));
        entrySet.add(new ConditionEntry<>(IdentityCondition.class,
                findPredicate(predicates, IdentityPredicate.class)));
        entrySet.add(new ConditionEntry<>(ChurchMembershipCondition.class,
                findPredicate(predicates, ChurchMembershipPredicate.class)));
        entrySet.add(new ConditionEntry<>(UnitMembershipCondition.class,
                findPredicate(predicates, UnitMembershipPredicate.class)));
        entrySet.add(new ConditionEntry<>(DepartmentIntegrationCondition.class,
                findPredicate(predicates, DepartmentIntegrationPredicate.class)));
    }

    @SuppressWarnings("unchecked")
    private <C extends Condition, P extends ConditionPredicate<C>> P findPredicate(
            List<ConditionPredicate<?>> predicates, Class<P> predicateClass) {
        return predicates.stream()
                .filter(conditionPredicate -> conditionPredicate.getClass().equals(predicateClass))
                .findFirst()
                .map(conditionPredicate -> (P) conditionPredicate)
                .orElseThrow(
                        () -> new IllegalStateException("Condition not found for class: " + predicateClass.getName()));
    }

    @SuppressWarnings("unchecked")
    public <C extends Condition> ConditionPredicate<C> getPredicate(final C condition) {
        return entrySet
                .stream()
                .filter(entry -> entry.getConditionClass().equals(condition.getClass()))
                .findFirst()
                .map(entry -> ((ConditionEntry<C>) entry).getPredicate())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No condition found for contract: " + condition.getClass().getName()));
    }

}
