package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.PersonSimpleDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class MembershipDto {

    private UUID id;
    private UUID unitId;
    private PersonSimpleDto person;
    private Affiliation affiliation;

    @Data
    public static class Detailed {
        private UUID id;
        private UnitDto unitDto;
        private PersonDto person;
        private Affiliation affiliation;
    }

}
