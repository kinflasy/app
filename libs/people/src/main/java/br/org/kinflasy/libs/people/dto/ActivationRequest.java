package br.org.kinflasy.libs.people.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ActivationRequest {

    private @NotBlank UUID inactivePersonId;
    private @NotBlank UUID userId;

}
