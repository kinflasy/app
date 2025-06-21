package br.org.kinflasy.api.entities.core.people_filter;

import java.util.function.Predicate;

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
    @JoinColumn(nullable = false)
    private PeopleFilter baseFilter;

    @Override
    public Predicate<Person> getPredicate() {
        // Negar resultado do filtro base
        return baseFilter.getPredicate().negate();
    }

}
