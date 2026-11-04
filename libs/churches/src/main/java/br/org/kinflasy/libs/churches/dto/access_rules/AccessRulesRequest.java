package br.org.kinflasy.libs.churches.dto.access_rules;

import java.util.List;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccessRulesRequest {

    private List<AccessRule> rules;

}
