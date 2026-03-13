package br.org.kinflasy.libs.churches.dto;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class MembershipDto {

    private UUID id;
    private UUID unitId;
    private PersonDto person;
    private Affiliation affiliation;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DetailingUnit extends MembershipDto {
        private UnitDto.Detailed unit;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Detailed extends MembershipDto {
        private UnitDto.Detailed unit;
    }

}
