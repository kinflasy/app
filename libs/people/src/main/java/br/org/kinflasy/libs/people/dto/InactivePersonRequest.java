package br.org.kinflasy.libs.people.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InactivePersonRequest extends PersonRequest {

    private @NotNull UUID churchId;
    private @Email String email;

    @Data
    @Accessors(chain = true)
    public static class FromUser {
        private @NotNull UUID churchId;
        private @NotNull UUID userId;
    }

}
