package br.org.kinflasy.api.dto.core.church.membership;


import java.util.UUID;

import br.org.kinflasy.api.apis.churches.entities.membership.Membership;
import br.org.kinflasy.api.dto.core.InactivePersonDTO;
import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.libs.churches.enums.membership.Affiliation;

public record MembershipDTO(
        UUID id,
        UnitDTO unit,
        InactivePersonDTO person,
        Affiliation status) {

    public static MembershipDTO ofNullable(final Membership membership) {
        return (membership != null) ? ofNonNull(membership) : null;
    }

    public static MembershipDTO ofNonNull(final Membership membership) {
        return new MembershipDTO(membership.getId(), UnitDTO.ofNonNull(membership.getUnit()),
                InactivePersonDTO.ofNonNull(membership.getPerson()), membership.getStatus());
    }

}
