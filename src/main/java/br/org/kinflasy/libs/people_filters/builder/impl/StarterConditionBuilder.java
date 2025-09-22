package br.org.kinflasy.libs.people_filters.builder.impl;

import java.util.UUID;
import java.util.function.Function;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people_filters.builder.contracts.AccumulatedConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.contracts.MultipleConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.contracts.SingleConditionBuilder;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StarterConditionBuilder implements SingleConditionBuilder {

    private final ConcreteOngoingConditionBuilder concrete;

    @Override
    public ReadyConditionBuilder not(final Function<SingleConditionBuilder, ReadyConditionBuilder> thePerson) {
        return new FilledConditionBuilder(concrete.not(thePerson));
    }

    @Override
    public ReadyConditionBuilder matchesAllConditions(
            final Function<MultipleConditionBuilder, AccumulatedConditionBuilder> thePerson) {
        return new FilledConditionBuilder(concrete.matchesAllConditions(thePerson));
    }

    @Override
    public ReadyConditionBuilder matchesAnyCondition(
            final Function<MultipleConditionBuilder, AccumulatedConditionBuilder> thePerson) {
        return new FilledConditionBuilder(concrete.matchesAnyCondition(thePerson));
    }

    @Override
    public ReadyConditionBuilder is(final UUID personId) {
        return new FilledConditionBuilder(concrete.is(personId));
    }

    @Override
    public ReadyConditionBuilder is(final PersonCharacteristic characteristic) {
        return new FilledConditionBuilder(concrete.is(characteristic));
    }

    @Override
    public ReadyConditionBuilder isMemberOfChurch(final UUID churchId, final Affiliation affiliation) {
        return new FilledConditionBuilder(concrete.isMemberOfChurch(churchId, affiliation));
    }

    @Override
    public ReadyConditionBuilder isMemberOfUnit(final UUID unitId, final Affiliation affiliation) {
        return new FilledConditionBuilder(concrete.isMemberOfUnit(unitId, affiliation));
    }

    @Override
    public ReadyConditionBuilder isIntegrantOfDepartment(final UUID departmentId,
            final IntegrationType integrationType) {
        return new FilledConditionBuilder(concrete.isIntegrantOfDepartment(departmentId, integrationType));
    }

}
