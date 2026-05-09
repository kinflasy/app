package br.org.kinflasy.libs.churches.dto.access_rules;

import java.text.MessageFormat;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class DepartmentRule implements AccessRule {

    public static final String PREFIX = "department";

    @NotNull
    private final UUID departmentId;

    @NotNull
    private final IntegrationType integrationType;

    private final CharacteristicCondition condition;

    public DepartmentRule(final UUID departmentId, final IntegrationType integrationType) {
        this(departmentId, integrationType, CharacteristicCondition.EVERYONE);
    }

    @Override
    public String getFgaUser() {
        return MessageFormat.format("{0}:{1}#{2}", PREFIX, departmentId, integrationType.toString().toLowerCase());
    }

}
