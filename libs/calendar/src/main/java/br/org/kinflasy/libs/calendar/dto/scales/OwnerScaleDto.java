package br.org.kinflasy.libs.calendar.dto.scales;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerScaleDto extends ScaleDto {

    private UUID calendarEventId;

}
