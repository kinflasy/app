package br.org.kinflasy.api.dto.core.church.membership;

import java.time.LocalDate;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.membership.Leave;
import br.org.kinflasy.api.utils.enums.core.church.membership.LeaveType;

public record LeaveDTO(
        @NonNull Integer id,
        @NonNull MembershipDTO membership,
        @NonNull LeaveType type,
        @NonNull LocalDate date,
        @Nullable String note) {

    public static @Nullable LeaveDTO ofNullable(final @Nullable Leave leave) {
        return (leave != null) ? ofNonNull(leave) : null;
    }

    public static @NonNull LeaveDTO ofNonNull(final @NonNull Leave leave) {
        return new LeaveDTO(leave.getId(), MembershipDTO.ofNonNull(leave.getMembership()), leave.getType(),
                leave.getDate(), leave.getNote());
    }

}
