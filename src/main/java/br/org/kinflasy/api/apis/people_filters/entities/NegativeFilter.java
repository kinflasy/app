package br.org.kinflasy.api.apis.people_filters.entities;

import java.util.function.Predicate;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.apis.people.entities.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "negative_filters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NegativeFilter extends PeopleFilter {

    @ManyToOne(optional = false)
    private PeopleFilter baseFilter;

    @Override
    public Predicate<Person> getPredicate() {
        // Negar resultado do filtro base
        return baseFilter.getPredicate().negate();
    }

    @Override
    public @NonNull String toString() {
        return "not " + baseFilter.toString();
    }

}
