package br.org.kinflasy.libs.churches.dto;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.churches.enums.membership.EntryMode;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MembershipRequest {

    @NotNull
    private UUID personId;

    @NotNull
    private Affiliation affiliation;

    private EntryMode entryMode;
    private LocalDate entryDate;

    @Data
    @Accessors(chain = true)
    public static class Register {
        @NotNull
        private InactivePersonRequest person;

        @NotNull
        private Affiliation affiliation;

        private EntryMode entryMode;
        private LocalDate entryDate;
    }

    @Data
    @Accessors(chain = true)
    public static class Join {
        @NotNull
        private Affiliation affiliation;
    }

}
