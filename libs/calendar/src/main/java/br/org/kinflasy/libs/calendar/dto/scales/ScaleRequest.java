package br.org.kinflasy.libs.calendar.dto.scales;

import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ScaleRequest {

    private UUID lineupId;

}
