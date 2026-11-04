package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeactivationRequest {

    private @NotBlank UUID userId;

}
