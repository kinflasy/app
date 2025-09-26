package br.org.kinflasy.libs.people_filters.conditions.business;

import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.Data;

@Data
public class CharacteristicCondition implements Condition {

    private final PersonCharacteristic characteristic;

}
