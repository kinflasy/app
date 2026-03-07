package br.org.kinflasy.libs.churches.dto.access_rules;

import java.util.UUID;

import br.org.kinflasy.libs.churches.contracts.access_rules.UnitLevelRule;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import lombok.Data;

@Data
public class UnitRule implements UnitLevelRule {

    private UUID unitId;
    private Affiliation affiliation;

}
