package br.org.kinflasy.apis.people_filters.factories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.entities.StoredAndConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredCharacteristicCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredChurchMembershipCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredDepartmentIntegrationCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredExtensionIntegrationInChurchCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredExtensionIntegrationInUnitCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredIdentityCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredNegativeCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredOrConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredUnitMembershipCondition;
import br.org.kinflasy.apis.people_filters.predicates.business.CharacteristicPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.ChurchMembershipPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.DepartmentIntegrationPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.ExtensionIntegrationInChurchPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.ExtensionIntegrationInUnitPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.FunctionalConditionPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.IdentityPredicate;
import br.org.kinflasy.apis.people_filters.predicates.business.UnitMembershipPredicate;
import br.org.kinflasy.apis.people_filters.predicates.logical.AndPredicateGroup;
import br.org.kinflasy.apis.people_filters.predicates.logical.NegativePredicate;
import br.org.kinflasy.apis.people_filters.predicates.logical.OrPredicateGroup;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people_filters.conditions.business.CharacteristicCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ChurchMembershipCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.DepartmentIntegrationCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrationInChurchCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrationInUnitCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.FunctionalCondition;
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
        private Class<? extends StoredCondition> entityClass;
        private ConditionPredicate<C> predicate;
    }

    private final Set<ConditionEntry<? extends Condition>> entrySet = new HashSet<>();

    public ConditionPredicateFactory(final List<ConditionPredicate<?>> predicates) {
        // Associar condições lógicas a seus predicados
        entrySet.add(new ConditionEntry<>(
                AndConditionGroup.class,
                StoredAndConditionGroup.class,
                findPredicateInstance(predicates, AndPredicateGroup.class)));
        entrySet.add(new ConditionEntry<>(
                OrConditionGroup.class,
                StoredOrConditionGroup.class,
                findPredicateInstance(predicates, OrPredicateGroup.class)));
        entrySet.add(new ConditionEntry<>(
                NegativeCondition.class,
                StoredNegativeCondition.class,
                findPredicateInstance(predicates, NegativePredicate.class)));

        // Associar condições de negócio a seus predicados
        entrySet.add(new ConditionEntry<>(
                FunctionalCondition.class,
                null,
                findPredicateInstance(predicates, FunctionalConditionPredicate.class)));
        entrySet.add(new ConditionEntry<>(
                CharacteristicCondition.class,
                StoredCharacteristicCondition.class,
                findPredicateInstance(predicates, CharacteristicPredicate.class)));
        entrySet.add(new ConditionEntry<>(
                IdentityCondition.class,
                StoredIdentityCondition.class,
                findPredicateInstance(predicates, IdentityPredicate.class)));
        entrySet.add(new ConditionEntry<>(
                ChurchMembershipCondition.class,
                StoredChurchMembershipCondition.class,
                findPredicateInstance(predicates, ChurchMembershipPredicate.class)));
        entrySet.add(new ConditionEntry<>(
                UnitMembershipCondition.class,
                StoredUnitMembershipCondition.class,
                findPredicateInstance(predicates, UnitMembershipPredicate.class)));
        entrySet.add(new ConditionEntry<>(
                DepartmentIntegrationCondition.class,
                StoredDepartmentIntegrationCondition.class,
                findPredicateInstance(predicates, DepartmentIntegrationPredicate.class)));
        entrySet.add(new ConditionEntry<>(
                ExtensionIntegrationInChurchCondition.class,
                StoredExtensionIntegrationInChurchCondition.class,
                findPredicateInstance(predicates,
                        ExtensionIntegrationInChurchPredicate.class)));
        entrySet.add(new ConditionEntry<>(
                ExtensionIntegrationInUnitCondition.class,
                StoredExtensionIntegrationInUnitCondition.class,
                findPredicateInstance(predicates, ExtensionIntegrationInUnitPredicate.class)));
    }

    @SuppressWarnings("unchecked")
    public <C extends Condition> ConditionPredicate<C> getPredicate(final C condition) {
        return entrySet
                .stream()
                .filter(entry -> entry.getConditionClass().equals(condition.getClass()))
                .findFirst()
                .map(entry -> ((ConditionEntry<C>) entry).getPredicate())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No predicate found for condition: " + condition.getClass().getName()));
    }

    @SuppressWarnings("unchecked")
    public <C extends Condition> Class<C> getConditionClass(final Class<? extends StoredCondition> entityClass) {
        return entrySet
                .stream()
                .filter(entry -> entry.getEntityClass().equals(entityClass))
                .findFirst()
                .map(entry -> ((ConditionEntry<C>) entry).getConditionClass())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No condition found for entity: " + entityClass.getClass().getName()));
    }

    @SuppressWarnings("unchecked")
    public <C extends Condition> Class<? extends StoredCondition> getEntityClass(final Class<C> conditionClass) {
        return entrySet
                .stream()
                .filter(entry -> entry.getConditionClass().equals(conditionClass))
                .findFirst()
                .map(entry -> ((ConditionEntry<C>) entry).getEntityClass())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No entity class found for condition: " + conditionClass.getClass().getName()));
    }

    @SuppressWarnings("unchecked")
    private <C extends Condition, P extends ConditionPredicate<C>> P findPredicateInstance(
            List<ConditionPredicate<?>> predicates, Class<P> predicateClass) {
        return predicates.stream()
                .filter(conditionPredicate -> conditionPredicate.getClass().equals(predicateClass))
                .findFirst()
                .map(conditionPredicate -> (P) conditionPredicate)
                .orElseThrow(
                        () -> new IllegalStateException("Condition not found for class: " + predicateClass.getName()));
    }

}
