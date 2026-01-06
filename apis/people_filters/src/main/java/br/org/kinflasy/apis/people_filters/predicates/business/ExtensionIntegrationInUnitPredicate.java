package br.org.kinflasy.apis.people_filters.predicates.business;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.clients.DepartmentClient;
import br.org.kinflasy.apis.people_filters.clients.MembershipClient;
import br.org.kinflasy.apis.people_filters.clients.UnitClient;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrationInUnitCondition;
import feign.FeignException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class ExtensionIntegrationInUnitPredicate implements ConditionPredicate<ExtensionIntegrationInUnitCondition> {

    private final UnitClient unitClient;
    private final MembershipClient membershipClient;
    private final DepartmentClient departmentClient;

    @Override
    public boolean test(final ExtensionIntegrationInUnitCondition condition, final PersonDto person) {
        try {
            final var membership = unitClient.findActiveMembership(condition.getUnitId(), person.getId());
            return membershipClient.listIntegrations(membership.getId()).stream()
                    .anyMatch(integration -> {
                        final var typeMatches = Optional
                                // Checar tipo de integração, caso precise
                                .ofNullable(condition.getType())
                                .map(conditionType -> conditionType.equals(integration.getType()))

                                // Ou autorizar, caso não precise
                                .orElse(true)
                                .booleanValue();

                        // Verificar se o departamento possui a extensão
                        if (typeMatches) {
                            // Apenas chamar o endpoint, pois se não possuir, ele retorna 404 e cai no catch
                            departmentClient.findExtension(integration.getDepartmentId(), condition.getExtension());
                            return true;
                        } else {
                            return false;
                        }
                    });
        } catch (final FeignException.NotFound e) {
            return false;
        }
    }

}
