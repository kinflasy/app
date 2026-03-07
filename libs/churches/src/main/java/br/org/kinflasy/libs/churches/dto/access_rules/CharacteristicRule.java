package br.org.kinflasy.libs.churches.dto.access_rules;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.people.enums.Gender;
import lombok.Data;

@Data
public class CharacteristicRule implements AccessRule {

    private Gender gender;
    private int minAge;
    private int maxAge;

}
