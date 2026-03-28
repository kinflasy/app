package br.org.kinflasy.dto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentCalendarEventDto extends CalendarEventDto {

    private UUID departmentId;

}
