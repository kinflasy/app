package br.org.kinflasy.libs.calendar.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventCollaborationRequest {

    @NotNull
    private UUID departmentId;

}
