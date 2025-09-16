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
@Table(name = "conditions_negative")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoredNegativeCondition extends StoredCondition {

    @ManyToOne(optional = false)
    private StoredCondition baseFilter;

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
