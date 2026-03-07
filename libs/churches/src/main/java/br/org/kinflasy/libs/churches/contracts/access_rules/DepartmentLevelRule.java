package br.org.kinflasy.libs.churches.contracts.access_rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.kinflasy.libs.churches.dto.access_rules.ChurchRule;
import br.org.kinflasy.libs.churches.dto.access_rules.DepartmentRule;
import br.org.kinflasy.libs.churches.dto.access_rules.UnitRule;

@JsonSubTypes({ @Type(name = "DEPARTMENT", value = DepartmentRule.class),
        @Type(name = "UNIT", value = UnitRule.class), @Type(name = "CHURCH", value = ChurchRule.class) })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type", visible = true)
public interface DepartmentLevelRule extends AccessRule {

}
