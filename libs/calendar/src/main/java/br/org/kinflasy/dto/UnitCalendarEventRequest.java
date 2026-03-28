package br.org.kinflasy.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnitCalendarEventRequest extends CalendarEventRequest {

    @NotNull
    private UUID unitId;

}
