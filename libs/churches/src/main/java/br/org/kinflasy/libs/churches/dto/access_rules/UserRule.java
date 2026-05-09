package br.org.kinflasy.libs.churches.dto.access_rules;

import java.text.MessageFormat;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
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

    @JsonCreator
    public UserRule(final String userId, final CharacteristicCondition condition) {
        this.userId = userId;
        this.condition = Optional.ofNullable(condition).orElse(CharacteristicCondition.EVERYONE);
    }

    public UserRule(final String userId) {
        this(userId, CharacteristicCondition.EVERYONE);
    }

    @Override
    public String getFgaUser() {
        return MessageFormat.format("{0}:{1}", PREFIX, userId);
    }

}
