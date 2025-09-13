package br.org.kinflasy.apis.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private @NotBlank String username;
    private @NotBlank String password;

}
