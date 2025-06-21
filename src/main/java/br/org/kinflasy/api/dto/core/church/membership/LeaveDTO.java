package br.org.kinflasy.api.dto.core.church.membership;

import java.time.LocalDate;


import br.org.kinflasy.api.entities.core.church.membership.Leave;
import br.org.kinflasy.api.utils.enums.core.church.membership.LeaveType;

public record LeaveDTO(
        Integer id,
        MembershipDTO membership,
        LeaveType type,
        LocalDate date,
        String note) {

    public static LeaveDTO ofNullable(final Leave leave) {
        return (leave != null) ? ofNonNull(leave) : null;
    }

    public static LeaveDTO ofNonNull(final Leave leave) {
        return new LeaveDTO(leave.getId(), MembershipDTO.ofNonNull(leave.getMembership()), leave.getType(),
                leave.getDate(), leave.getNote());
    }

}
