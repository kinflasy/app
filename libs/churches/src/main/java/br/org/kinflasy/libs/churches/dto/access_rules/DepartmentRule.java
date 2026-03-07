package br.org.kinflasy.libs.churches.dto.access_rules;

import java.util.UUID;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartmentRule implements AccessRule {

    @NotNull
    private final UUID departmentId;

    @NotNull
    private final IntegrationType integrationType;

}
