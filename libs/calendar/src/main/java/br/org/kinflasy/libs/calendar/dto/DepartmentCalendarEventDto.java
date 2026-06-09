package br.org.kinflasy.libs.calendar.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentCalendarEventDto extends CalendarEventDto {

    private UUID departmentId;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Detailed extends CalendarEventDto {
        private DepartmentDto.CleanWithUnit department;
    }

}
