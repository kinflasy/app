package br.org.kinflasy.libs.churches.dto;

import br.org.kinflasy.libs.people.ids.PersonId;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeactivationRequest {

    private @NotBlank PersonId userId;

}
