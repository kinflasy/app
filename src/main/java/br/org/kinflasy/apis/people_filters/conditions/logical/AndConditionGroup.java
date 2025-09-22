package br.org.kinflasy.apis.people_filters.conditions.logical;

import java.util.List;

import br.org.kinflasy.apis.people_filters.conditions.structure.Condition;
import br.org.kinflasy.apis.people_filters.conditions.structure.ConditionGroup;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AndConditionGroup extends ConditionGroup {

    public AndConditionGroup(final List<Condition> conditions) {
        super(conditions);
    }

    @Override
    public boolean test(final PersonDto person) {
        return getConditions().stream()

                // Combinar todos os predicados com AND (lista vazia retorna true)
                .allMatch(filter -> filter.test(person));
    }

    @Override
    public String toString() {
        final var result = new StringBuilder("matches all:\n");

        final var textList = getConditions().stream()
                .map(filter -> "  - " + filter.toString().replace("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
