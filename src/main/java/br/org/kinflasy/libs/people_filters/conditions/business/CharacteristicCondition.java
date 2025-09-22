package br.org.kinflasy.libs.people_filters.conditions.business;

import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CharacteristicCondition extends Condition {

    private final PersonCharacteristic characteristic;

}
