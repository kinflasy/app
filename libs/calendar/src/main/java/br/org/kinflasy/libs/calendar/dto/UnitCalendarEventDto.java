package br.org.kinflasy.libs.calendar.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.dto.UnitDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UnitCalendarEventDto extends CalendarEventDto {

    private UUID unitId;

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Detailed extends CalendarEventDto {
        private UnitDto.CleanWithChurch unit;
    }

}
