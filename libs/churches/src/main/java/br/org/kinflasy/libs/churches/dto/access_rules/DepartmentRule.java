package br.org.kinflasy.libs.churches.dto.access_rules;

import java.text.MessageFormat;
import java.util.UUID;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import dev.openfga.sdk.api.client.model.ClientRelationshipCondition;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DepartmentRule implements AccessRule {

    public static final String PREFIX = "department";

    @NotNull
    private final UUID departmentId;

    @NotNull
    private final IntegrationType integrationType;

    private final CharacteristicRule condition;

    public DepartmentRule(final UUID departmentId, final IntegrationType integrationType) {
        this(departmentId, integrationType, CharacteristicRule.EVERYONE);
    }

    @Override
    public String getFgaUser() {
        return MessageFormat.format("{0}:{1}#{2}", PREFIX, departmentId, integrationType.toString().toLowerCase());
    }

    @Override
    public ClientRelationshipCondition getFgaCondition() {
        return condition.writeCondition();
    }

}
