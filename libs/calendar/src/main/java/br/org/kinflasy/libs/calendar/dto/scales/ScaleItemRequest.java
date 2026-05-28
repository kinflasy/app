package br.org.kinflasy.libs.calendar.dto.scales;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScaleItemRequest {

    @NotNull
    private UUID roleId;

    @NotNull
    private UUID personId;

}
