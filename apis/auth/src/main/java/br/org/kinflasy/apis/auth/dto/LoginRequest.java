package br.org.kinflasy.apis.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    private String username;
    
    @NotBlank
    @ToString.Exclude
    private String password;

}
