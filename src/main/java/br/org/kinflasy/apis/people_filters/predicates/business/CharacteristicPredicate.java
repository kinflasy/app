package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Component
public class CharacteristicPredicate extends ConditionPredicate {

    private final PersonCharacteristic characteristic;

    @Override
    public boolean test(final PersonDto person) {
        return characteristic.test(person);
    }

    @Override
    public @NonNull String toString() {
        return "is " + characteristic.toString();
    }

}
