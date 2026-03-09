package br.org.kinflasy.libs.churches.dto.access_rules;

import java.util.List;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import lombok.Data;

@Data
public class AccessRulesRequest {

    private List<AccessRule> rules;

}
