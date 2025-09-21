package br.org.kinflasy.libs.people_filters.contracts.business;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.people_filters.contracts.structure.ConditionContract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DepartmentIntegrationContract extends ConditionContract {

    private final UUID departmentId;
    private final IntegrationType type;

}
