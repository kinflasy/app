package br.org.kinflasy.libs.calendar.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.dto.UnitDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnitCalendarEventDto extends CalendarEventDto {

    private UUID unitId;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Detailed extends CalendarEventDto {
        private UnitDto.Clean unit;
    }

}
