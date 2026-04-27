package br.org.kinflasy.libs.calendar.dto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnitCalendarEventDto extends CalendarEventDto {

    private UUID unitId;

}
