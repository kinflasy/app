package br.org.kinflasy.libs.people.dto.roles;

import java.util.UUID;

import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.ids.PersonId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AbilityDto {

    private UUID id;
    private PersonId personId;
    private UUID roleId;

    @Data
    @Accessors(chain = true)
    public static class DetailingPerson {
        private UUID id;
        private PersonDto person;
        private UUID roleId;
    }

    @Data
    @Accessors(chain = true)
    public static class DetailingRole {
        private UUID id;
        private PersonId personId;
        private RoleDto role;
    }

    @Data
    @Accessors(chain = true)
    public static class Detailed {
        private UUID id;
        private PersonDto person;
        private RoleDto role;
    }

}
