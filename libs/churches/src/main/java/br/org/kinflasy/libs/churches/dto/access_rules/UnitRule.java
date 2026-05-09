package br.org.kinflasy.libs.churches.dto.access_rules;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UnitRule implements AccessRule {

    public static final String PREFIX = "unit";

    @NotNull
    private final UUID unitId;

    @NotNull
    private final Affiliation affiliation;

    private final CharacteristicCondition condition;

    @JsonCreator
    public UnitRule(final UUID unitId, final Affiliation affiliation, final CharacteristicCondition condition) {
        this.unitId = unitId;
        this.affiliation = affiliation;
        this.condition = Optional.ofNullable(condition).orElse(CharacteristicCondition.EVERYONE);
    }

    public UnitRule(final UUID unitId, final Affiliation affiliation) {
        this(unitId, affiliation, CharacteristicCondition.EVERYONE);
    }

    @Override
    public String getFgaUser() {
        return MessageFormat.format("{0}:{1}#{2}", PREFIX, unitId, affiliation.toString().toLowerCase());
    }

}
