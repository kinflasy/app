package br.org.kinflasy.apis.people_filters.predicates.logical;

import java.util.List;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicateGroup;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AndPredicateGroup extends ConditionPredicateGroup {

    public AndPredicateGroup(final List<ConditionPredicate> conditions) {
        super(conditions);
    }

    @Override
    public boolean test(final PersonDto person) {
        return getPredicates().stream()

                // Combinar todos os predicados com AND (lista vazia retorna true)
                .allMatch(filter -> filter.test(person));
    }

    @Override
    public String toString() {
        final var result = new StringBuilder("matches all:\n");

        final var textList = getPredicates().stream()
                .map(filter -> "  - " + filter.toString().replace("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
