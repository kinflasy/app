package br.org.kinflasy.api.entities.core.peopleFilter;

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
@Table(name = "or_group_people_filters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class OrGroupPeopleFilter extends PeopleFilter {

    @ManyToMany
    private @NonNull List<PeopleFilter> filters;

    @Override
    public @NonNull Function<Person, Boolean> getFilter() {
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

}
