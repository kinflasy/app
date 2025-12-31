package br.org.kinflasy.libs.people.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InactivePersonRequest extends PersonRequest {

    private @NotNull UUID churchId;
    private @Email String email;

}
