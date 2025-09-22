package br.org.kinflasy.apis.people_filters.conditions.business;

import java.util.UUID;

import br.org.kinflasy.apis.people_filters.conditions.structure.Condition;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class DepartmentIntegrationCondition extends Condition {

    private final UUID departmentId;
    private final IntegrationType type;

}
