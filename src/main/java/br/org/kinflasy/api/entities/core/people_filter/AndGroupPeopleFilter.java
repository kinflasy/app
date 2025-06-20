package br.org.kinflasy.api.entities.core.people_filter;

import java.util.List;
import java.util.function.Function;

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
    private List<PeopleFilter> filters;

    @Override
    public Function<Person, Boolean> getFilter() {
        return (person -> {
            // Iniciar com true (valor neutro do AND)
            var result = true;

            // Aplicar cada filtro
            for (final var filter : filters) {
                result &= filter.getFilter().apply(person);
            }

            // Retornar
            return result;
        });
    }

}
