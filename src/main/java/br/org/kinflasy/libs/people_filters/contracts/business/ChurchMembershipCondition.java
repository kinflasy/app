package br.org.kinflasy.libs.people_filters.contracts.business;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people_filters.contracts.structure.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChurchMembershipCondition extends Condition {

    private final UUID churchId;
    private final Affiliation affiliation;

}
