package br.org.kinflasy.apis.people_filters.conditions.logical;

import java.util.List;

import org.springframework.lang.NonNull;

import br.org.kinflasy.apis.people_filters.conditions.structure.Condition;
import br.org.kinflasy.apis.people_filters.conditions.structure.ConditionGroup;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrConditionGroup extends ConditionGroup {

    public OrConditionGroup(final List<Condition> conditions) {
        super(conditions);
    }

    @Override
    public boolean test(final PersonDto person) {
        return getConditions().stream()

                // Combinar todos os predicados com OR (lista vazia retorna false)
                .anyMatch(filter -> filter.test(person));
    }

    @Override
    public @NonNull String toString() {
        final var result = new StringBuilder("matches any:\n");

        final var textList = getConditions().stream()
                .map(filter -> "  - " + filter.toString().replace("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
