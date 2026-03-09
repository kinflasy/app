package br.org.kinflasy.libs.churches.dto.access_rules;

import java.text.MessageFormat;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import dev.openfga.sdk.api.client.model.ClientRelationshipCondition;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRule implements AccessRule {

    private static final String PREFIX = "user";
    private static final String ALL = "*";
    public static final UserRule EVERYONE = new UserRule(ALL);

    @NotNull
    private final String userId;

    private final CharacteristicRule condition;

    public static UserRule everyoneWith(final CharacteristicRule characteristic) {
        return new UserRule(ALL, characteristic);
    }

    public UserRule(final String userId) {
        this(userId, CharacteristicRule.EVERYONE);
    }

    @Override
    public String getFgaUser() {
        return MessageFormat.format("{0}:{1}", PREFIX, userId);
    }

    @Override
    public ClientRelationshipCondition getFgaCondition() {
        return condition.writeCondition();
    }

}
