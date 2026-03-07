package br.org.kinflasy.libs.churches.dto.access_rules;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.people.enums.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CharacteristicRule implements AccessRule {

    public static final CharacteristicRule EVERYONE = new CharacteristicRule(null, 0, 0);

    private final Gender gender;

    @Min(0)
    @Max(120)
    private final int minAge;

    @Min(0)
    @Max(120)
    private final int maxAge;

}
