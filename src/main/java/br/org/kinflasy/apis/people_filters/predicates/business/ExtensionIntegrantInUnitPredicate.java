package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.apis.churches.services.department.IntegrationService;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrantInUnitCondition;
import lombok.Data;

@Data
@Component
public class ExtensionIntegrantInUnitPredicate implements ConditionPredicate<ExtensionIntegrantInUnitCondition> {

    private final IntegrationService service;
    private final DepartmentService departmentService;

    @Override
    public boolean test(final ExtensionIntegrantInUnitCondition condition, final PersonDto person) {
        return service.listByPerson(person.getId()).stream()
                .anyMatch(integration -> {
                    // Se o departamento for desta unidade
                    if (integration.getDepartment().getUnitId().equals(condition.getUnitId())) {

                        // Se contiver a extensão
                        return departmentService
                                .findExtension(integration.getDepartment().getId(), condition.getExtension())
                                .map(ignoredSubscription -> {

                                    // Se a pessoa tiver o tipo de integração pedido (ou não for pedido nenhum
                                    // específico)
                                    if (condition.getType() != null) {
                                        return condition.getType().equals(integration.getType());
                                    } else {
                                        return true;
                                    }
                                })
                                .orElse(false);
                    } else {
                        return false;
                    }
                });
    }

}
