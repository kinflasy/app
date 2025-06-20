package br.org.kinflasy.api.entities.core.peopleFilter;

import java.util.function.Function;

import br.org.kinflasy.api.entities.core.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "negative_filters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class NegativeFilter extends PeopleFilter {

    @ManyToOne
    @JoinColumn(name = "filter", nullable = false)
    private PeopleFilter filter;

    @Override
    public Function<Person, Boolean> getFilter() {
        // Negar resultado do filtro base
        return (person -> !filter.getFilter().apply(person));
    }

}
