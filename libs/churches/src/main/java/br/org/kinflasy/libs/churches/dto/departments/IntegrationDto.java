package br.org.kinflasy.libs.churches.dto.departments;

import java.util.UUID;

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

    public static class Pending extends IntegrationDto {

    }

}
