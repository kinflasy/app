package br.org.kinflasy.libs.churches.dto.access_rules;

import java.util.UUID;

import br.org.kinflasy.libs.churches.contracts.access_rules.DepartmentLevelRule;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import lombok.Data;

@Data
public class DepartmentRule implements DepartmentLevelRule {

    private UUID departmentId;
    private IntegrationType integrationType;

}
