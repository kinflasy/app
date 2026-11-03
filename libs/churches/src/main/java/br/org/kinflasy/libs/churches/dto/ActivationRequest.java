package br.org.kinflasy.libs.churches.dto;

import br.org.kinflasy.libs.people.ids.PersonId;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ActivationRequest {

    private @NotBlank PersonId inactivePersonId;
    private @NotBlank PersonId userId;

    @Data
    @Accessors(chain = true)
    public static class WithUsername {
        private @NotBlank PersonId inactivePersonId;
        private @NotBlank String username;
    }

}
