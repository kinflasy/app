package br.org.kinflasy.libs.churches.dto;

import java.time.LocalDate;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.churches.enums.membership.EntryMode;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import br.org.kinflasy.libs.people.ids.PersonId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MembershipRequest {

    @NotNull
    private PersonId personId;

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
