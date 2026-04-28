package br.org.kinflasy.libs.churches.dto;

import java.time.LocalDateTime;
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

    public Simple toSimple() {
        return new Simple()
                .setId(id)
                .setUnitId(unitId)
                .setPersonId(person.getId())
                .setAffiliation(affiliation);
    }

    @Data
    public static class Simple {
        private UUID id;
        private UUID unitId;
        private UUID personId;
        private Affiliation affiliation;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class SimplePending extends Simple {
        private LocalDateTime unitConfirmationDate;
        private LocalDateTime userConfirmationDate;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Pending extends MembershipDto {
        private LocalDateTime unitConfirmationDate;
        private LocalDateTime userConfirmationDate;
    }

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
