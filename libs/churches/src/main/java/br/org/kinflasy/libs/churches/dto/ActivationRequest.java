package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActivationRequest {

    private @NotBlank UUID inactivePersonId;
    private @NotBlank UUID userId;

    @Data
    public static class WithUsername {
        private @NotBlank UUID inactivePersonId;
        private @NotBlank String username;
    }

}
