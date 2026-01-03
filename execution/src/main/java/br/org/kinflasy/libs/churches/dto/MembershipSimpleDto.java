package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MembershipSimpleDto {

    private UUID id;
    private UUID unitId;
    private UUID personId;
    private Affiliation affiliation;

}
