package br.org.kinflasy.libs.people_filters.contracts.business;

import br.org.kinflasy.libs.people_filters.contracts.structure.ConditionContract;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CharacteristicContract extends ConditionContract {

    private final PersonCharacteristic characteristic;

}
