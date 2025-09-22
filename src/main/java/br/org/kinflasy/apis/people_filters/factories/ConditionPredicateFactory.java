package br.org.kinflasy.apis.people_filters.factories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Component
public class ConditionPredicateFactory {

    private final Map<Class<? extends Condition>, ConditionPredicate> map;

    public ConditionPredicateFactory(final List<ConditionPredicate> predicates) {
        // Inicializar mapa de chave-valor
        map = new HashMap<>();

        // Associar condições lógicas a seus predicados
        map.put(AndConditionGroup.class, findCondition(predicates, AndPredicateGroup.class));
        map.put(OrConditionGroup.class, findCondition(predicates, OrPredicateGroup.class));
        map.put(NegativeCondition.class, findCondition(predicates, NegativePredicate.class));

        // Associar condições de negócio a seus predicados
        map.put(CharacteristicCondition.class, findCondition(predicates, CharacteristicPredicate.class));
        map.put(IdentityCondition.class, findCondition(predicates, IdentityPredicate.class));
        map.put(ChurchMembershipCondition.class, findCondition(predicates, ChurchMembershipPredicate.class));
        map.put(UnitMembershipCondition.class, findCondition(predicates, UnitMembershipPredicate.class));
        map.put(DepartmentIntegrationCondition.class, findCondition(predicates, DepartmentIntegrationPredicate.class));
    }

    @SuppressWarnings("unchecked")
    private <P extends ConditionPredicate> P findCondition(List<ConditionPredicate> predicates,
            Class<P> predicateClass) {
        return predicates.stream()
                .filter(conditionPredicate -> conditionPredicate.getClass().equals(predicateClass))
                .findFirst()
                .map(conditionPredicate -> (P) conditionPredicate)
                .orElseThrow(
                        () -> new IllegalStateException("Condition not found for class: " + predicateClass.getName()));
    }

    public ConditionPredicate getCondition(Condition contract) {
        final var condition = map.get(contract.getClass());
        if (condition == null) {
            throw new IllegalArgumentException("No condition found for contract: " + contract.getClass().getName());
        }
        // Aqui você 'transforma' o contract em uma condition
        // return condition.withContract(contract);
        // Você pode ter um método na
        // interface Condition para injetar os dados
        // do contract

        return null;
    }

}
