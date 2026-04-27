package br.org.kinflasy.libs.calendar.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CalendarEventRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @NotEmpty
    private List<AccessRule> visibilityRules = Collections.emptyList();

}
