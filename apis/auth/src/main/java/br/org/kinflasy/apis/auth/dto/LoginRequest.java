package br.org.kinflasy.apis.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    private String username;
    
    @NotBlank
    @ToString.Exclude
    private String password;

}
