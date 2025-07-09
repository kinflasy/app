package br.org.kinflasy.api.libs.churches.dto.membership;

import java.util.UUID;

import br.org.kinflasy.api.libs.churches.dto.UnitDto;
import br.org.kinflasy.api.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.api.libs.people.dto.PersonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MembershipDto {

    private UUID id;
    private UnitDto unit;
    private PersonDto person;
    private Affiliation status;

}
