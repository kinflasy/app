package br.org.kinflasy.api.dto.core.church.membership;

import java.time.LocalDate;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.membership.Leave;
import br.org.kinflasy.api.utils.enums.core.church.membership.LeaveType;

public record LeaveDTO(
        Integer id,
        MembershipDTO membership,
        LeaveType type,
        LocalDate date,
        @Nullable String note) {

    public static @Nullable LeaveDTO ofNullable(final @Nullable Leave leave) {
        return (leave != null) ? ofNonNull(leave) : null;
    }

    public static LeaveDTO ofNonNull(final Leave leave) {
        return new LeaveDTO(leave.getId(), MembershipDTO.ofNonNull(leave.getMembership()), leave.getType(),
                leave.getDate(), leave.getNote());
    }

}
