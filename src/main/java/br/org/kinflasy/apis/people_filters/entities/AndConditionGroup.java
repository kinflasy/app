package br.org.kinflasy.apis.people_filters.entities;

import br.org.kinflasy.libs.people.dto.PersonDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "conditions_and_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class AndConditionGroup extends ConditionGroup {

    @Override
    public boolean test(final PersonDto person) {
        return getFilters().stream()

                // Combinar todos os predicados com AND (lista vazia retorna true)
                .allMatch(filter -> filter.test(person));
    }

    @Override
    public String toString() {
        final var result = new StringBuilder("matches all:\n");

        final var textList = getFilters().stream()
                .map(filter -> "  - " + filter.toString().replace("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
