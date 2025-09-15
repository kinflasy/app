package br.org.kinflasy.apis.people_filters.entities;

import br.org.kinflasy.libs.people.dto.PersonDto;
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
    public boolean test(final PersonDto person) {
        // Negar resultado do filtro base
        return baseFilter.negate().test(person);
    }

    @Override
    public String toString() {
        return "not " + baseFilter.toString();
    }

}
