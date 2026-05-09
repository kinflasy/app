package br.org.kinflasy.libs.churches.dto.access_rules;

import java.text.MessageFormat;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
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

    private final CharacteristicCondition condition;

    public static UserRule everyoneWith(final CharacteristicCondition characteristic) {
        return new UserRule(ALL, characteristic);
    }

    public UserRule(final String userId) {
        this(userId, CharacteristicCondition.EVERYONE);
    }

    @Override
    public String getFgaUser() {
        return MessageFormat.format("{0}:{1}", PREFIX, userId);
    }

}
