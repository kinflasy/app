package br.org.kinflasy.libs.people_filters.conditions;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnitMembershipCondition extends Condition {

    private final UUID unitId;

    private final Affiliation affiliation;

    @Override
    public boolean test(final PersonDto person) {
        // TODO implementar filtro
        return false;
    }

}
