package br.org.kinflasy.apis.people_filters.predicates.logical;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.factories.ConditionFactory;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicateGroup;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.logical.AndConditionGroup;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class AndPredicateGroup implements ConditionPredicateGroup<AndConditionGroup> {

    private ConditionFactory factory;

    @Override
    public boolean test(final AndConditionGroup condition, final PersonDto person) {
        return condition.getConditions().stream()

                // Obter predicate e mapear para tupla
                .map(innerCondition -> new ConditionPredicateGroup.Tuple<>(innerCondition,
                        factory.getPredicate(innerCondition)))

                // Combinar todos os predicados com AND (lista vazia retorna true)
                .allMatch(tuple -> tuple.getPredicate().test(tuple.getCondition(), person));
    }

}
