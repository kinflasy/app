package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import java.util.UUID;
import java.util.function.Function;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people_filters.contracts.business.CharacteristicCondition;
import br.org.kinflasy.libs.people_filters.contracts.business.ChurchMembershipCondition;
import br.org.kinflasy.libs.people_filters.contracts.business.DepartmentIntegrationCondition;
import br.org.kinflasy.libs.people_filters.contracts.business.IdentityCondition;
import br.org.kinflasy.libs.people_filters.contracts.business.UnitMembershipCondition;
import br.org.kinflasy.libs.people_filters.contracts.logical.NegativeCondition;
import br.org.kinflasy.libs.people_filters.contracts.structure.Condition;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.AccumulatedConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.MultipleConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.OngoingConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.SingleConditionBuilder;
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
