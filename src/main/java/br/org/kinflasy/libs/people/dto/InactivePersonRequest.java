package br.org.kinflasy.libs.people.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InactivePersonRequest extends PersonRequest {

    private @Email String email;

}
