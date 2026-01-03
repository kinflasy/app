package br.org.kinflasy.libs.people_filters.conditions.business;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentIntegrationCondition implements Condition {

    private UUID departmentId;
    private IntegrationType type;

}
