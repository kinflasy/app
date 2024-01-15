package br.org.kinflasy.api.entities.core.peopleFilter;

import java.util.List;
import java.util.function.Function;

import br.org.kinflasy.api.entities.core.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "or_group_people_filter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class OrGroupPeopleFilter extends PeopleFilter {

    @ManyToMany
    private List<PeopleFilter> filters;

    @Override
    public Function<User, Boolean> getFilter() {
        return (user -> {
            // Iniciar com false (valor neutro do OR)
            var result = false;

            // Apicar cada filtro
            for (final var filter : filters) {
                result |= filter.getFilter().apply(user);
            }

            // Retornar
            return result;
        });
    }

}
