package br.org.kinflasy.apis.people_filters.predicates.business;

import java.util.UUID;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class UnitMembershipPredicate extends ConditionPredicate {

    private final UUID unitId;
    private final Affiliation affiliation;

}
