package br.org.kinflasy.libs.people.dto.roles;

import java.util.UUID;

import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.Data;

@Data
public class AbilityDto {

    private UUID id;
    private UUID personId;
    private UUID roleId;

    @Data
    public static class DetailingPerson {
        private UUID id;
        private PersonDto person;
        private UUID roleId;
    }

    @Data
    public static class DetailingRole {
        private UUID id;
        private UUID personId;
        private RoleDto role;
    }

    @Data
    public static class Detailed {
        private UUID id;
        private PersonDto person;
        private RoleDto role;
    }

}
