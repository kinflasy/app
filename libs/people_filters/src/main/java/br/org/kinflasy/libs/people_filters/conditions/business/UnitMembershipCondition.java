package br.org.kinflasy.libs.people_filters.conditions.business;

import java.util.UUID;

import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import lombok.Value;

@Value
public class UnitMembershipCondition implements Condition {

    private final UUID unitId;
    private final Affiliation affiliation;

}
