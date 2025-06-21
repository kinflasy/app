package br.org.kinflasy.api.entities.core.people_filter;

import java.util.List;
import java.util.function.Predicate;

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
    private List<PeopleFilter> baseFilters;

    @Override
    public Predicate<Person> getPredicate() {
        return baseFilters.stream()

                // Desencapsular o predicado de cada filtro
                .map(PeopleFilter::getPredicate)

                // Aplicar OR a cada predicado
                .reduce(Predicate::or)

                // Definir true quando não houver filtros internos
                .orElse(any -> true);
    }

}
