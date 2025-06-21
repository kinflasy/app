package br.org.kinflasy.api.entities.core.people_filter;

import java.util.function.Predicate;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
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
    public Predicate<Person> getPredicate() {
        return (person -> {
            // Iniciar com false (valor neutro do OR)
            var result = false;

            // Apicar cada filtro
            for (final var filter : getFilters()) {
                result |= filter.getPredicate().test(person);
            }

            // Retornar
            return result;
        });
    }

    @Override
    public @NonNull String toString() {
        final var result = new StringBuilder("matches at least one:\n");

        final var textList = getFilters().stream()
                .map(filter -> "  - " + filter.toString().replace("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
