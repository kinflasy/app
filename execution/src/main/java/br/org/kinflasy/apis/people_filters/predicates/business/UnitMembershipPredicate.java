package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.churches.services.UnitService;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.UnitMembershipCondition;
import lombok.Data;

@Data
@Component
public class UnitMembershipPredicate implements ConditionPredicate<UnitMembershipCondition> {

    private final UnitService service;

    @Override
    public boolean test(final UnitMembershipCondition condition, final PersonDto person) {
        return service.findActiveMembership(condition.getUnitId(), person.getId())
                .map(membership -> {
                    if (condition.getAffiliation() != null) {
                        return membership.getAffiliation().includes(condition.getAffiliation());
                    } else {
                        return true;
                    }
                })
                .orElse(false);
    }

}
