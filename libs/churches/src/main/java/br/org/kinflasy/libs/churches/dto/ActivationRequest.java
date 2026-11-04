package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ActivationRequest {

    private @NotBlank UUID inactivePersonId;
    private @NotBlank UUID userId;

    @Data
    @Accessors(chain = true)
    public static class WithUsername {
        private @NotBlank UUID inactivePersonId;
        private @NotBlank String username;
    }

}
