package br.org.kinflasy.libs.churches.dto.access_rules;

import java.util.UUID;

import br.org.kinflasy.libs.churches.contracts.access_rules.ChurchLevelRule;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import lombok.Data;

@Data
public class ChurchRule implements ChurchLevelRule {

    private UUID churchId;
    private Affiliation affiliation;

}
