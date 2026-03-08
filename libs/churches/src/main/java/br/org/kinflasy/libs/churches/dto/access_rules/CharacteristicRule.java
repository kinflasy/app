package br.org.kinflasy.libs.churches.dto.access_rules;

import java.util.Map;
import java.util.Optional;

import br.org.kinflasy.libs.people.enums.Gender;
import dev.openfga.sdk.api.client.model.ClientRelationshipCondition;
import dev.openfga.sdk.api.model.RelationshipCondition;
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

    @SuppressWarnings("unchecked")
    public static CharacteristicRule of(final RelationshipCondition condition) {
        return of((Map<String, Object>) condition.getContext());
    }

    public static CharacteristicRule of(final Map<String, Object> condition) {
        final var genderAsString = (String) condition.get("req_gender");
        final var gender = genderAsString.equals("") ? null : Gender.valueOf(genderAsString);
        return new CharacteristicRule(gender, (int) condition.get("min_age"),
                (int) condition.get("max_age"));
    }

    public ClientRelationshipCondition writeCondition() {
        final var genderName = Optional.ofNullable(gender)
                .map(Object::toString)
                .orElse("");

        return new ClientRelationshipCondition()
                .name("characteristics")
                .context(Map.of("req_gender", genderName, "min_age", minAge, "max_age", maxAge));
    }

}
