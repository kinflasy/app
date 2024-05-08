package br.org.kinflasy.api.entities.core.peoplefilter;

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

@Entity
@Table(name = "and_group_people_filters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class AndGroupPeopleFilter extends PeopleFilter {

    @ManyToMany
    private @NonNull List<PeopleFilter> filters;

    @Override
    public @NonNull Function<Person, Boolean> getFilter() {
        return (person -> {
            // Iniciar com true (valor neutro do AND)
            var result = true;

            // Apicar cada filtro
            for (final var filter : filters) {
                result &= filter.getFilter().apply(person);
            }

            // Retornar
            return result;
        });
    }

    @Override
    public @NonNull String toString() {
        final var indent = "  ";

        final var result = new StringBuilder("matches all: ");

        for (final var filter : filters) {
            final var internal = filter.toString();
            internal.replaceAll("\n", "\n" + indent);
            result.append(indent + "- " + filter.toString());
        }

        return result.toString();
    }

}
