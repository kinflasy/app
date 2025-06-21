package br.org.kinflasy.api.entities.core.people_filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "and_group_people_filters")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class AndGroupPeopleFilter extends GroupablePeopleFilter {

    @ManyToMany
    private List<PeopleFilter> filters;

    @Override
    public Predicate<Person> getPredicate() {
        return (person -> {
            // Iniciar com true (valor neutro do AND)
            var result = true;

            // Apicar cada filtro
            for (final var filter : filters) {
                result &= filter.getPredicate().test(person);
            }

            // Retornar
            return result;
        });
    }

    @Override
    public @NonNull String toString() {
        final var result = new StringBuilder("matches all:\n");

        final var textList = filters.stream()
                .map(filter -> "  - " + filter.toString().replace("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
