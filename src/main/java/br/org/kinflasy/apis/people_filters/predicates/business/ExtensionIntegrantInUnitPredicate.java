package br.org.kinflasy.apis.people_filters.predicates.business;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.apis.churches.services.department.IntegrationService;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrantInUnitCondition;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class ExtensionIntegrantInUnitPredicate implements ConditionPredicate<ExtensionIntegrantInUnitCondition> {

    private final IntegrationService service;
    private final DepartmentService departmentService;

    @Override
    public boolean test(final ExtensionIntegrantInUnitCondition condition, final PersonDto person) {
        return service.listByPerson(person.getId()).stream()
                .anyMatch(integration -> Optional
                        // Verificar integração da pessoa com o departamento
                        .ofNullable(condition.getType())
                        .map(conditionType -> conditionType.equals(integration.getType()))
                        // Se o tipo for nulo na condição, todos os tipos de integração têm acesso
                        .or(() -> Optional.of(true))
                        // Avançar só se o resultado for positivo
                        .filter(sameTypeOrAny -> sameTypeOrAny)

                        // Verificar se o departamento é desta unidade
                        .flatMap(ignore -> departmentService.findById(integration.getDepartmentId()))
                        .filter(department -> department.getUnitId().equals(condition.getUnitId()))

                        // Verificar se o departamento possui a extensão
                        .flatMap(department -> departmentService
                                .findExtension(department.getId(), condition.getExtension()))

                        // Obter resultado
                        .isPresent());
    }

}
