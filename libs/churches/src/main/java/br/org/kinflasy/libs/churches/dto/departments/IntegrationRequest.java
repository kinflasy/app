package br.org.kinflasy.libs.churches.dto.departments;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class IntegrationRequest {

    private @NotBlank UUID membershipId;
    private @NotNull IntegrationType type;

}
