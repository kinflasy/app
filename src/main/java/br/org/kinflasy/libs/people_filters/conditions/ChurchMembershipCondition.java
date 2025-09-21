package br.org.kinflasy.libs.people_filters.conditions;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class ChurchMembershipCondition extends Condition {

    private final UUID churchId;
    private final Affiliation affiliation;

}
