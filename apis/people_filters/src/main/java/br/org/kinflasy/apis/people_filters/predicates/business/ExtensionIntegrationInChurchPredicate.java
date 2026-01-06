package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.clients.ChurchClient;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrationInChurchCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrationInUnitCondition;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class ExtensionIntegrationInChurchPredicate implements ConditionPredicate<ExtensionIntegrationInChurchCondition> {

    private final ChurchClient client;
    private final ExtensionIntegrationInUnitPredicate extensionIntegrantInUnitPredicate;

    @Override
    public boolean test(final ExtensionIntegrationInChurchCondition condition, final PersonDto person) {
        return client.listUnits(condition.getChurchId()).stream()
                .anyMatch(unit -> {
                    final var unitCondition = new ExtensionIntegrationInUnitCondition(unit.getId(),
                            condition.getExtension(), condition.getType());

                    return extensionIntegrantInUnitPredicate.test(unitCondition, person);
                });
    }

}
