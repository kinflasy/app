package br.org.kinflasy.libs.churches.dto.access_rules;

import java.text.MessageFormat;
import java.util.UUID;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import dev.openfga.sdk.api.client.model.ClientRelationshipCondition;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UnitRule implements AccessRule {

    public static final String PREFIX = "unit";

    @NotNull
    private final UUID unitId;

    @NotNull
    private final Affiliation affiliation;

    private final CharacteristicRule condition;

    public UnitRule(final UUID unitId, final Affiliation affiliation) {
        this(unitId, affiliation, CharacteristicRule.EVERYONE);
    }

    @Override
    public String getFgaUser() {
        return MessageFormat.format("{0}:{1}#{2}", PREFIX, unitId, affiliation.toString().toLowerCase());
    }

    @Override
    public ClientRelationshipCondition getFgaCondition() {
        return condition.writeCondition();
    }

}
