package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.churches.services.department.IntegrationService;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.DepartmentIntegrationCondition;
import lombok.Data;

@Data
@Component
public class DepartmentIntegrationPredicate implements ConditionPredicate<DepartmentIntegrationCondition> {

    private final IntegrationService service;

    @Override
    public boolean test(final DepartmentIntegrationCondition condition, final PersonDto person) {
        return service.findIntegration(condition.getDepartmentId(), person.getId())
                .map(integration -> {
                    if (condition.getType() != null) {
                        return condition.getType().equals(integration.getType());
                    } else {
                        return true;
                    }
                })
                .orElse(false);
    }

}
