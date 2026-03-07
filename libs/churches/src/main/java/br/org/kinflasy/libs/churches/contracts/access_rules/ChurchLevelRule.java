package br.org.kinflasy.libs.churches.contracts.access_rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.kinflasy.libs.churches.dto.access_rules.ChurchRule;

@JsonSubTypes(@Type(name = "CHURCH", value = ChurchRule.class))
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type", visible = true)
public interface ChurchLevelRule extends UnitLevelRule {

}
