package br.org.kinflasy.libs.churches.dto.access_rules;

import java.text.MessageFormat;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class ChurchRule implements AccessRule {

    public static final String PREFIX = "church";

    @NotNull
    private final UUID churchId;

    @NotNull
    private final Affiliation affiliation;

    private final CharacteristicCondition condition;

    public ChurchRule(final UUID churchId, final Affiliation affiliation) {
        this(churchId, affiliation, CharacteristicCondition.EVERYONE);
    }

    @Override
    public String getFgaUser() {
        return MessageFormat.format("{0}:{1}#{2}", PREFIX, churchId, affiliation.toString().toLowerCase());
    }

}
