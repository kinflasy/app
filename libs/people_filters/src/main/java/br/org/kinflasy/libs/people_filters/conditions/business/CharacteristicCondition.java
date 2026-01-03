package br.org.kinflasy.libs.people_filters.conditions.business;

import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacteristicCondition implements Condition {

    private PersonCharacteristic characteristic;

}
