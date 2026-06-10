package br.org.kinflasy.libs.calendar.dto.scales;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CollaboratorScaleDto extends ScaleDto {

    private UUID collaborationId;
    private UUID calendarEventId;

}
