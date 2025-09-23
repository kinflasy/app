package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.DepartmentIntegrationCondition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
public class DepartmentIntegrationPredicate implements ConditionPredicate<DepartmentIntegrationCondition> {

    @Override
    public boolean test(final DepartmentIntegrationCondition condition, final PersonDto person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'test'");
    }

}
