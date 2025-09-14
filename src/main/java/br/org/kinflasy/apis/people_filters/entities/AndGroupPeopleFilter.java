package br.org.kinflasy.apis.people_filters.entities;

import java.util.function.Predicate;

import org.springframework.lang.NonNull;

import br.org.kinflasy.libs.people.dto.PersonDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "and_group_people_filters")
@Data
@EqualsAndHashCode(callSuper = true)
public class AndGroupPeopleFilter extends GroupablePeopleFilter {

    @Override
    public Predicate<PersonDto> getPredicate() {
        return person -> getFilters().stream()

                // Combinar todos os predicados com AND (lista vazia retorna true)
                .allMatch(filter -> filter.getPredicate().test(person));
    }

    @Override
    public @NonNull String toString() {
        final var result = new StringBuilder("matches all:\n");

        final var textList = getFilters().stream()
                .map(filter -> "  - " + filter.toString().replace("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
