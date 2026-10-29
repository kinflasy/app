package br.org.kinflasy.libs.calendar.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EventCollaborationDto {

    private UUID id;
    private UUID calendarEventId;
    private UUID departmentId;

    @Data
    @Accessors(chain = true)
    public static class DetailingDepartment {
        private UUID id;
        private UUID calendarEventId;
        private DepartmentDto department;
    }

    @Data
    @Accessors(chain = true)
    public static class DetailingEvent {
        private UUID id;
        private CalendarEventDto calendarEvent;
        private UUID departmentId;
    }

    @Data
    @Accessors(chain = true)
    public static class Detailed {
        private UUID id;
        private CalendarEventDto calendarEvent;
        private DepartmentDto department;
    }

}
