package br.org.kinflasy.libs.churches.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.PersonIdentifierDto;
import br.org.kinflasy.libs.people.dto.UserIdentifierDto;
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
    private PersonIdentifierDto person;
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
    public static class Pending {
        private UUID id;
        private UUID unitId;
        private UserIdentifierDto person;
        private Affiliation affiliation;
        private LocalDateTime unitConfirmationDate;
        private LocalDateTime userConfirmationDate;
    }

    @Data
    public static class IdentifyingPerson {
        private UUID id;
        private UUID unitId;
        private PersonIdentifierDto person;
        private Affiliation affiliation;

        /**
         * @deprecated Use {@link #person} instead, which contains more information
         *             about the person. This field will be removed in future versions.
         */
        @Deprecated(forRemoval = true)
        private UUID personId;
    }

    @Data
    public static class DetailingPerson {
        private UUID id;
        private UUID unitId;
        private PersonDto person;
        private Affiliation affiliation;
    }

    @Data
    public static class DetailingUnit {
        private UUID id;
        private UnitDto.Detailed unit;
        private PersonIdentifierDto person;
        private Affiliation affiliation;
    }

    @Data
    public static class Detailed {
        private UUID id;
        private UnitDto.Detailed unit;
        private PersonDto person;
        private Affiliation affiliation;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class WithPhone extends MembershipDto {
        private String phone;
    }

}
