package br.org.kinflasy.api.libs.churches.dto.membership;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.api.libs.churches.enums.membership.EntryType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntryDto {

    private UUID id;
    private MembershipDto membership;
    private EntryType type;
    private LocalDate date;

}
