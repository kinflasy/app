package br.org.kinflasy.api.dto.core.church.membership;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.InactivePersonDTO;
import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.entities.core.church.membership.Membership;
import br.org.kinflasy.api.utils.enums.core.church.membership.Status;

public record MembershipDTO(
        @NonNull Integer id,
        @NonNull UnitDTO unit,
        @NonNull InactivePersonDTO person,
        @NonNull Status status) {

    public static @Nullable MembershipDTO ofNullable(final @Nullable Membership membership) {
        return (membership != null) ? ofNonNull(membership) : null;
    }

    public static @NonNull MembershipDTO ofNonNull(final @NonNull Membership membership) {
        return new MembershipDTO(membership.getId(), UnitDTO.ofNonNull(membership.getUnit()),
                InactivePersonDTO.ofNonNull(membership.getPerson()), membership.getStatus());
    }

}
