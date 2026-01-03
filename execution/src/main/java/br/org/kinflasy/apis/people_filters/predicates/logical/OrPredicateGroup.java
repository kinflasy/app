package br.org.kinflasy.apis.people_filters.predicates.logical;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.factories.ConditionFactory;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicateGroup;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.base_conditions.conditions.logical.OrConditionGroup;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class OrPredicateGroup implements ConditionPredicateGroup<OrConditionGroup> {

    private ConditionFactory factory;

    @Override
    public boolean test(final OrConditionGroup condition, final PersonDto person) {
        return condition.getConditions().stream()

                // Obter predicate e mapear para tupla
                .map(innerCondition -> new ConditionPredicateGroup.Tuple<>(innerCondition,
                        factory.getPredicate(innerCondition)))

                // Combinar todos os predicados com OR (lista vazia retorna false)
                .anyMatch(tuple -> tuple.getPredicate().test(tuple.getCondition(), person));
    }

}
