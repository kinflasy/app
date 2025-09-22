package br.org.kinflasy.apis.people_filters.conditions.logical;

import br.org.kinflasy.apis.people_filters.conditions.structure.Condition;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NegativeCondition extends Condition {

    private final Condition baseFilter;

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
