package br.org.kinflasy.libs.churches.dto.departments;

import java.util.UUID;

import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IntegrationDto {

    private UUID id;
    private UUID departmentId;
    private UUID membershipId;
    private IntegrationType type;

    @Data
    @NoArgsConstructor
    public static class Pending {
        private UUID id;
        private UUID departmentId;
        private UUID membershipId;
    }

    @Data
    @NoArgsConstructor
    public static class Detailed {
        private UUID id;
        private DepartmentDto department;
        private MembershipDto.WithPhone membership;
        private IntegrationType type;
    }

}
