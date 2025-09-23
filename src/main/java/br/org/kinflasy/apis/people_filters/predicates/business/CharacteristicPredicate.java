package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.CharacteristicCondition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
public class CharacteristicPredicate implements ConditionPredicate<CharacteristicCondition> {

    @Override
    public boolean test(final CharacteristicCondition condition, final PersonDto person) {
        return condition.getCharacteristic().test(person);
    }

}
