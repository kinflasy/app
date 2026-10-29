package br.org.kinflasy.libs.calendar.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EventCollaborationRequest {

    @NotNull
    private UUID departmentId;

}
