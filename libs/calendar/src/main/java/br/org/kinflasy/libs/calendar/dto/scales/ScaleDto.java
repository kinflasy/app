package br.org.kinflasy.libs.calendar.dto.scales;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@Data
@JsonSubTypes({
        @Type(name = "OWNER", value = OwnerScaleDto.class),
        @Type(name = "COLLABORATOR", value = CollaboratorScaleDto.class) })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public class ScaleDto {

    private UUID id;
    private UUID lineupId;

}
