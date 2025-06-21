package br.org.kinflasy.api.dto.core.church.membership;


import br.org.kinflasy.api.dto.core.InactivePersonDTO;
import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.entities.core.church.membership.Membership;
import br.org.kinflasy.api.utils.enums.core.church.membership.Status;

public record MembershipDTO(
        Integer id,
        UnitDTO unit,
        InactivePersonDTO person,
        Status status) {

    public static MembershipDTO ofNullable(final Membership membership) {
        return (membership != null) ? ofNonNull(membership) : null;
    }

    public static MembershipDTO ofNonNull(final Membership membership) {
        return new MembershipDTO(membership.getId(), UnitDTO.ofNonNull(membership.getUnit()),
                InactivePersonDTO.ofNonNull(membership.getPerson()), membership.getStatus());
    }

}
