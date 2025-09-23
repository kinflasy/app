package br.org.kinflasy.libs.people_filters.conditions.business;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnitMembershipCondition implements Condition {

    private final UUID unitId;
    private final Affiliation affiliation;

}
