package br.org.kinflasy.libs.base_conditions.conditions.structure;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import br.org.kinflasy.libs.base_conditions.conditions.logical.AndConditionGroup;
import br.org.kinflasy.libs.base_conditions.conditions.logical.NegativeCondition;
import br.org.kinflasy.libs.base_conditions.conditions.logical.OrConditionGroup;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = AndConditionGroup.class, name = "AND"),
        @Type(value = OrConditionGroup.class, name = "OR"),
        @Type(value = NegativeCondition.class, name = "NOT")
})
public interface Condition {

}
