package br.org.kinflasy.apis.people_filters.predicates.business;

import java.util.UUID;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class DepartmentIntegrationPredicate extends ConditionPredicate {

    private final UUID departmentId;
    private final IntegrationType type;

}
