package br.org.kinflasy.api.entities.core.people_filter;

import java.util.List;
import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "or_group_people_filters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OrGroupPeopleFilter extends GroupablePeopleFilter {

    @ManyToMany
    private List<PeopleFilter> filters;

    @Override
    public Function<Person, Boolean> getFilter() {
        return (person -> {
            // Iniciar com false (valor neutro do OR)
            var result = false;

            // Apicar cada filtro
            for (final var filter : filters) {
                result |= filter.getFilter().apply(person);
            }

            // Retornar
            return result;
        });
    }

    @Override
    public @NonNull String toString() {
        final var result = new StringBuilder("matches at least one:\n");

        final var textList = filters.stream()
                .map(filter -> "  - " + filter.toString().replaceAll("\n", "\n  "))
                .toList();

        final var text = String.join("\n", textList);

        result.append(text);

        return result.toString();
    }

}
