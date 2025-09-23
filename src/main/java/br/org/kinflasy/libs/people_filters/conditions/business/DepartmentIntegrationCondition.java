package br.org.kinflasy.libs.people_filters.conditions.business;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentIntegrationCondition implements Condition {

    private final UUID departmentId;
    private final IntegrationType type;

}
