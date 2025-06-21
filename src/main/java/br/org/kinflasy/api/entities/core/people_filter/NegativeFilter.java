package br.org.kinflasy.api.entities.core.people_filter;

import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "negative_group_people_filters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NegativeFilter extends PeopleFilter {

    @ManyToOne(optional = false)
    private PeopleFilter filter;

    @Override
    public Function<Person, Boolean> getFilter() {
        // Negar resultado do filtro base
        return (person -> !filter.getFilter().apply(person));
    }

    @Override
    public @NonNull String toString() {
        return "not " + filter.toString();
    }

}
