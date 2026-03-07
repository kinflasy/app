package br.org.kinflasy.libs.churches.dto.access_rules;

import java.util.UUID;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChurchRule implements AccessRule {

    @NotNull
    private final UUID churchId;

    @NotNull
    private final Affiliation affiliation;

}
