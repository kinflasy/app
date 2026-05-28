package br.org.kinflasy.libs.calendar.dto.scales;

import java.util.UUID;

import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.roles.RoleDto;
import lombok.Data;

@Data
public class ScaleItemDto {

    private UUID id;
    private UUID scaleId;
    private UUID roleId;
    private UUID personId;

    @Data
    public static class DetailingScaleAndRole {
        private UUID id;
        private ScaleDto scale;
        private RoleDto role;
        private UUID personId;
    }

    @Data
    public static class DetailingPersonAndRole {
        private UUID id;
        private UUID scaleId;
        private RoleDto role;
        private PersonDto person;
    }

    @Data
    public static class Detailed {
        private UUID id;
        private ScaleDto scale;
        private RoleDto role;
        private PersonDto person;
    }

}
