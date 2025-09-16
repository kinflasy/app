package br.org.kinflasy.libs.people_filters.conditions;

import br.org.kinflasy.libs.people.dto.PersonDto;

public interface ConditionTester {

    boolean isMemberOfChurch(ChurchMembershipCondition condition, PersonDto person);

    boolean isMemberOfUnit(UnitMembershipCondition condition, PersonDto person);

    boolean isIntegrantOfDepartment(DepartmentIntegrationCondition condition, PersonDto person);

}
