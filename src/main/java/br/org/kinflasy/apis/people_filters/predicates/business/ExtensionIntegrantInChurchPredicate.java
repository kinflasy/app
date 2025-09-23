package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.churches.services.ChurchService;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrantInChurchCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrantInUnitCondition;
import lombok.Data;

@Data
@Component
public class ExtensionIntegrantInChurchPredicate implements ConditionPredicate<ExtensionIntegrantInChurchCondition> {

    private final ChurchService service;
    private final ExtensionIntegrantInUnitPredicate extensionIntegrantInUnitPredicate;

    @Override
    public boolean test(final ExtensionIntegrantInChurchCondition condition, final PersonDto person) {
        return service.listUnits(condition.getChurchId()).stream()
                .anyMatch(unit -> {
                    final var unitCondition = new ExtensionIntegrantInUnitCondition(unit.getId(),
                            condition.getExtension(), condition.getType());

                    return extensionIntegrantInUnitPredicate.test(unitCondition, person);
                });
    }

}
