package br.org.kinflasy.libs.calendar.dto.scales;

import java.util.UUID;

import br.org.kinflasy.libs.people.ids.PersonId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ScaleItemRequest {

    @NotNull
    private UUID roleId;

    @NotNull
    private PersonId personId;

}
