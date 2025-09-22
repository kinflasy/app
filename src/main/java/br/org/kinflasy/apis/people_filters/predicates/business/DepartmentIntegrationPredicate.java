package br.org.kinflasy.apis.people_filters.predicates.business;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Component
public abstract class DepartmentIntegrationPredicate extends ConditionPredicate {

    private final UUID departmentId;
    private final IntegrationType type;

}
