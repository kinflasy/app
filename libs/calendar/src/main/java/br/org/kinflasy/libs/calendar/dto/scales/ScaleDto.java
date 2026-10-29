package br.org.kinflasy.libs.calendar.dto.scales;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.kinflasy.libs.calendar.dto.CalendarEventDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleDto.DetailingCalendarEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonSubTypes({
        @Type(name = "OWNER", value = OwnerScaleDto.class),
        @Type(name = "COLLABORATOR", value = CollaboratorScaleDto.class),
        @Type(name = "DETAILING_CALENDAR_EVENT", value = DetailingCalendarEvent.class)
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public class ScaleDto {

    private UUID id;
    private UUID lineupId;

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class DetailingCalendarEvent extends ScaleDto {
        private CalendarEventDto calendarEvent;
    }

}
