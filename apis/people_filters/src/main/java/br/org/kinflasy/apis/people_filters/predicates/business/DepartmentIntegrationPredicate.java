package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.clients.DepartmentClient;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.DepartmentIntegrationCondition;
import feign.FeignException;
import lombok.Data;

@Data
@Component
public class DepartmentIntegrationPredicate implements ConditionPredicate<DepartmentIntegrationCondition> {

    private final DepartmentClient client;

    @Override
    public boolean test(final DepartmentIntegrationCondition condition, final PersonDto person) {
        try {
            // Obter os dados de integração dessa pessoa nesse departamento
            final var integration = client.findIntegration(condition.getDepartmentId(), person.getId());

            // Pular checagem de tipo de integração, caso a condição não exija
            if (condition.getType() == null) {
                return true;
            }

            // Verificar se o tipo de integração condiz
            return integration.getType().includes(condition.getType());
        } catch (final FeignException.NotFound e) {
            return false;
        }
    }

}
