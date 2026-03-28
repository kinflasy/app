package br.org.kinflasy.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class CalendarEventDto {

    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

}
