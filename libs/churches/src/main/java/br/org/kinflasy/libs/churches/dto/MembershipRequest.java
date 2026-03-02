package br.org.kinflasy.libs.churches.dto;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.churches.enums.membership.EntryMode;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MembershipRequest {

    private @NotBlank UUID personId;
    private @NotNull Affiliation affiliation;
    private EntryMode entryMode;
    private LocalDate entryDate;

    @Data
    public static class Register {
        private @NotNull InactivePersonRequest person;
        private @NotNull Affiliation affiliation;
        private EntryMode entryMode;
        private LocalDate entryDate;
    }

}
