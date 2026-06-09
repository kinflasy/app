package br.org.kinflasy.libs.calendar.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import lombok.Data;

@Data
@JsonSubTypes({
        @Type(name = "DEPARTMENT", value = DepartmentCalendarEventDto.class),
        @Type(name = "DEPARTMENT_DETAILED", value = DepartmentCalendarEventDto.Detailed.class),
        @Type(name = "UNIT", value = UnitCalendarEventDto.class),
        @Type(name = "UNIT_DETAILED", value = UnitCalendarEventDto.Detailed.class),
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public class CalendarEventDto {

    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<AccessRule> visibilityRules;
    private UUID cardImageId;

}
