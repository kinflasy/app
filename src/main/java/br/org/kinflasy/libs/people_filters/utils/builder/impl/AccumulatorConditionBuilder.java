package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people_filters.contracts.structure.ConditionContract;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.AccumulatedConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.MultipleConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.SingleConditionBuilder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AccumulatorConditionBuilder implements MultipleConditionBuilder, AccumulatedConditionBuilder {

    private final ConcreteOngoingConditionBuilder concrete;

    protected final List<ConditionContract> conditions = new ArrayList<>();

    @Override
    public AccumulatedConditionBuilder not(final Function<SingleConditionBuilder, ReadyConditionBuilder> thePerson) {
        conditions.add(concrete.not(thePerson));
        return this;
    }

    @Override
    public AccumulatedConditionBuilder matchesAllConditions(
            final Function<MultipleConditionBuilder, AccumulatedConditionBuilder> thePerson) {
        conditions.add(concrete.matchesAllConditions(thePerson));
        return this;
    }

    @Override
    public AccumulatedConditionBuilder matchesAnyCondition(
            final Function<MultipleConditionBuilder, AccumulatedConditionBuilder> thePerson) {
        conditions.add(concrete.matchesAnyCondition(thePerson));
        return this;
    }

    @Override
    public AccumulatedConditionBuilder is(final UUID personId) {
        conditions.add(concrete.is(personId));
        return this;
    }

    @Override
    public AccumulatedConditionBuilder is(final PersonCharacteristic characteristic) {
        conditions.add(concrete.is(characteristic));
        return this;
    }

    @Override
    public AccumulatedConditionBuilder isMemberOfChurch(final UUID churchId, final Affiliation affiliation) {
        conditions.add(concrete.isMemberOfChurch(churchId, affiliation));
        return this;
    }

    @Override
    public AccumulatedConditionBuilder isMemberOfUnit(final UUID unitId, final Affiliation affiliation) {
        conditions.add(concrete.isMemberOfUnit(unitId, affiliation));
        return this;
    }

    @Override
    public AccumulatedConditionBuilder isIntegrantOfDepartment(final UUID departmentId,
            final IntegrationType integrationType) {
        conditions.add(concrete.isIntegrantOfDepartment(departmentId, integrationType));
        return this;
    }

}
