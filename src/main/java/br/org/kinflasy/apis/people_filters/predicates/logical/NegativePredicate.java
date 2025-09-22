package br.org.kinflasy.apis.people_filters.predicates.logical;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NegativePredicate extends ConditionPredicate {

    private final ConditionPredicate basePredicate;

    @Override
    public boolean test(final PersonDto person) {
        // Negar resultado do filtro base
        return basePredicate.negate().test(person);
    }

    @Override
    public String toString() {
        return "not " + basePredicate.toString();
    }

}
