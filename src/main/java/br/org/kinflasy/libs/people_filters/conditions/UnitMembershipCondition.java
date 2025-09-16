package br.org.kinflasy.libs.people_filters.conditions;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnitMembershipCondition extends Condition {

    @JsonIgnore
    private final ConditionTester tester;

    private final UUID unitId;

    private final Affiliation affiliation;

    @Override
    public boolean test(final PersonDto person) {
        return tester.isMemberOfUnit(this, person);
    }

}
