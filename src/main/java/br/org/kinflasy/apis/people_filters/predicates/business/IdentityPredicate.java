package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.IdentityCondition;
import lombok.Data;

@Data
@Component
public class IdentityPredicate implements ConditionPredicate<IdentityCondition> {

    @Override
    public boolean test(final IdentityCondition condition, final PersonDto person) {
        return person.getId().equals(condition.getPersonId());
    }

}
