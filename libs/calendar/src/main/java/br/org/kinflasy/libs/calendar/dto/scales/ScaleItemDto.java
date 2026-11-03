package br.org.kinflasy.libs.calendar.dto.scales;

import java.util.UUID;

import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.roles.RoleDto;
import br.org.kinflasy.libs.people.ids.PersonId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ScaleItemDto {

    private UUID id;
    private UUID scaleId;
    private UUID roleId;
    private PersonId personId;

    @Data
    @Accessors(chain = true)
    public static class DetailingScaleAndRole {
        private UUID id;
        private ScaleDto scale;
        private RoleDto role;
        private PersonId personId;
    }

    @Data
    @Accessors(chain = true)
    public static class DetailingPersonAndRole {
        private UUID id;
        private UUID scaleId;
        private RoleDto role;
        private PersonDto person;
    }

    @Data
    @Accessors(chain = true)
    public static class Detailed {
        private UUID id;
        private ScaleDto scale;
        private RoleDto role;
        private PersonDto person;
    }

}
