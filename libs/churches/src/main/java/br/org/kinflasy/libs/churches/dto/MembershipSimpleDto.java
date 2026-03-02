package br.org.kinflasy.libs.churches.dto;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MembershipSimpleDto {

    private UUID id;
    private UUID unitId;
    private UUID personId;
    private Affiliation affiliation;

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Pending extends MembershipSimpleDto {
        private LocalDate unitConfirmationDate;
        private LocalDate userConfirmationDate;
    }

}
