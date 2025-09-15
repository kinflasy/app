package br.org.kinflasy.apis.people_filters.entities;

import org.springframework.lang.NonNull;

import br.org.kinflasy.libs.people.dto.PersonDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "or_group_people_filters")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrGroupPeopleFilter extends GroupablePeopleFilter {

    @Override
    public boolean test(final PersonDto person) {
        return getFilters().stream()

                // Combinar todos os predicados com OR (lista vazia retorna false)
                .anyMatch(filter -> filter.test(person));
    }

    @Override
    public @NonNull String toString() {
        final var result = new StringBuilder("matches any:\n");

        final var textList = getFilters().stream()
                .map(filter -> "  - " + filter.toString().replace("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
