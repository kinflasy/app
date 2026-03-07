package br.org.kinflasy.libs.churches.dto.access_rules;

import java.util.Map;
import java.util.Optional;

import br.org.kinflasy.libs.people.enums.Gender;
import dev.openfga.sdk.api.client.model.ClientRelationshipCondition;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CharacteristicRule {

    public static final CharacteristicRule EVERYONE = new CharacteristicRule(null, 0, 0);

    private final Gender gender;

    @Min(0)
    @Max(120)
    private final int minAge;

    @Min(0)
    @Max(120)
    private final int maxAge;

    public ClientRelationshipCondition writeCondition() {
        final var genderName = Optional.ofNullable(gender)
                .map(Object::toString)
                .orElse("");

        return new ClientRelationshipCondition()
                .name("characteristics")
                .context(Map.of("req_gender", genderName, "min_age", minAge, "max_age", maxAge));
    }

}
