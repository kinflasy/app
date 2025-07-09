package br.org.kinflasy.api.libs.churches.dto.membership;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.api.libs.churches.enums.membership.LeaveType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeaveDto {

    private UUID id;
    private MembershipDto membership;
    private LeaveType type;
    private LocalDate date;
    private String note;

}
