package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonSimpleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MembershipDto {

    private UUID id;
    private UUID unitId;
    private PersonSimpleDto person;
    private Affiliation affiliation;

}
