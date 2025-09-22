package br.org.kinflasy.apis.people_filters.predicates.logical;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicateGroup;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class OrPredicateGroup extends ConditionPredicateGroup {

    public OrPredicateGroup(final List<ConditionPredicate> conditions) {
        super(conditions);
    }

    @Override
    public boolean test(final PersonDto person) {
        return getPredicates().stream()

                // Combinar todos os predicados com OR (lista vazia retorna false)
                .anyMatch(filter -> filter.test(person));
    }

    @Override
    public @NonNull String toString() {
        final var result = new StringBuilder("matches any:\n");

        final var textList = getPredicates().stream()
                .map(filter -> "  - " + filter.toString().replace("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
