package br.org.kinflasy.libs.people_filters.builder.impl;

import java.util.UUID;
import java.util.function.Function;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people_filters.builder.contracts.AccumulatedConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.contracts.MultipleConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.contracts.OngoingConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.builder.contracts.SingleConditionBuilder;
import br.org.kinflasy.libs.people_filters.conditions.business.CharacteristicCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ChurchMembershipCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.DepartmentIntegrationCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.IdentityCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.UnitMembershipCondition;
import br.org.kinflasy.libs.people_filters.conditions.logical.NegativeCondition;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConcreteOngoingConditionBuilder implements OngoingConditionBuilder<Condition> {

    @Override
    public Condition not(Function<SingleConditionBuilder, ReadyConditionBuilder> thePerson) {
        final var baseCondition = thePerson.apply(new StarterConditionBuilder(this)).build();
        return new NegativeCondition(baseCondition);
    }

    @Override
    public Condition matchesAllConditions(Function<MultipleConditionBuilder, AccumulatedConditionBuilder> thePerson) {
        return thePerson.apply(new AndConditionBuilder(this)).join().build();
    }

    @Override
    public Condition matchesAnyCondition(Function<MultipleConditionBuilder, AccumulatedConditionBuilder> thePerson) {
        return thePerson.apply(new OrConditionBuilder(this)).join().build();
    }

    @Override
    public Condition is(UUID personId) {
        return new IdentityCondition(personId);
    }

    @Override
    public Condition is(PersonCharacteristic characteristic) {
        return new CharacteristicCondition(characteristic);
    }

    @Override
    public Condition isMemberOfChurch(UUID churchId, Affiliation affiliation) {
        return new ChurchMembershipCondition(churchId, affiliation);
    }

    @Override
    public Condition isMemberOfUnit(UUID unitId, Affiliation affiliation) {
        return new UnitMembershipCondition(unitId, affiliation);
    }

    @Override
    public Condition isIntegrantOfDepartment(UUID departmentId, IntegrationType integrationType) {
        return new DepartmentIntegrationCondition(departmentId, integrationType);
    }

}
