package br.org.kinflasy.libs.churches.dto;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.churches.enums.membership.EntryMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MembershipRequest {

    private @NotBlank UUID personId;
    private @NotNull Affiliation affiliation;
    private EntryMode entryMode;
    private LocalDate entryDate;

}
