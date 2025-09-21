package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import java.util.UUID;
import java.util.function.Function;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people_filters.contracts.business.CharacteristicContract;
import br.org.kinflasy.libs.people_filters.contracts.business.ChurchMembershipContract;
import br.org.kinflasy.libs.people_filters.contracts.business.DepartmentIntegrationContract;
import br.org.kinflasy.libs.people_filters.contracts.business.IdentityContract;
import br.org.kinflasy.libs.people_filters.contracts.business.UnitMembershipContract;
import br.org.kinflasy.libs.people_filters.contracts.logical.NegativeContract;
import br.org.kinflasy.libs.people_filters.contracts.structure.ConditionContract;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.AccumulatedConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.MultipleConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.OngoingConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.SingleConditionBuilder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConcreteOngoingConditionBuilder implements OngoingConditionBuilder<ConditionContract> {

    @Override
    public ConditionContract not(Function<SingleConditionBuilder, ReadyConditionBuilder> thePerson) {
        final var baseCondition = thePerson.apply(new StarterConditionBuilder(this)).build();
        return new NegativeContract(baseCondition);
    }

    @Override
    public ConditionContract matchesAllConditions(Function<MultipleConditionBuilder, AccumulatedConditionBuilder> thePerson) {
        return thePerson.apply(new AndConditionBuilder(this)).join().build();
    }

    @Override
    public ConditionContract matchesAnyCondition(Function<MultipleConditionBuilder, AccumulatedConditionBuilder> thePerson) {
        return thePerson.apply(new OrConditionBuilder(this)).join().build();
    }

    @Override
    public ConditionContract is(UUID personId) {
        return new IdentityContract(personId);
    }

    @Override
    public ConditionContract is(PersonCharacteristic characteristic) {
        return new CharacteristicContract(characteristic);
    }

    @Override
    public ConditionContract isMemberOfChurch(UUID churchId, Affiliation affiliation) {
        return new ChurchMembershipContract(churchId, affiliation);
    }

    @Override
    public ConditionContract isMemberOfUnit(UUID unitId, Affiliation affiliation) {
        return new UnitMembershipContract(unitId, affiliation);
    }

    @Override
    public ConditionContract isIntegrantOfDepartment(UUID departmentId, IntegrationType integrationType) {
        return new DepartmentIntegrationContract(departmentId, integrationType);
    }

}
