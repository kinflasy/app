package br.org.kinflasy.libs.people.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends PersonRequest {

    private @NotBlank String username;
    private @NotBlank @Email String email;
    private LocalDateTime emailVerifiedAt;
    private @NotBlank String password;

}
