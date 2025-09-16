package br.org.kinflasy.libs.people_filters.utils.builder.contracts;

import java.util.UUID;
import java.util.function.Function;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;

public interface OngoingConditionBuilder<T extends ConditionBuilder> extends ConditionBuilder {

    T not(Function<SingleConditionBuilder, ReadyConditionBuilder> condition);

    T matchesAllConditions(Function<MultipleConditionBuilder, AccumulatedConditionBuilder> conditions);

    T matchesAnyCondition(Function<MultipleConditionBuilder, AccumulatedConditionBuilder> conditions);

    T is(UUID personId);

    T is(PersonCharacteristic characteristic);

    T isMemberOfChurch(UUID churchId, Affiliation... affiliation);

    T isMemberOfUnit(UUID unitId, Affiliation... affiliation);

    T isIntegrantOfDepartment(UUID departmentId, IntegrationType... integrationTypes);

}
