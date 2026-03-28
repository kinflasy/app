package br.org.kinflasy.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentCalendarEventRequest extends CalendarEventRequest {

    @NotNull
    private UUID departmentId;

}
