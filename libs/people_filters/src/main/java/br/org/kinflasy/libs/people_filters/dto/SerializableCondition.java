package br.org.kinflasy.libs.people_filters.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import br.org.kinflasy.libs.base_conditions.conditions.logical.AndConditionGroup;
import br.org.kinflasy.libs.base_conditions.conditions.logical.NegativeCondition;
import br.org.kinflasy.libs.base_conditions.conditions.logical.OrConditionGroup;
import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.people_filters.conditions.business.CharacteristicCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ChurchMembershipCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.DepartmentIntegrationCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrationInChurchCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.ExtensionIntegrationInUnitCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.IdentityCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.UnitMembershipCondition;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = AndConditionGroup.class, name = "AND"),
        @Type(value = OrConditionGroup.class, name = "OR"),
        @Type(value = NegativeCondition.class, name = "NOT"),
        @Type(value = CharacteristicCondition.class, name = "CHARACTERISTIC"),
        @Type(value = ChurchMembershipCondition.class, name = "CHURCH_MEMBERSHIP"),
        @Type(value = DepartmentIntegrationCondition.class, name = "DEPARTMENT_INTEGRATION"),
        @Type(value = ExtensionIntegrationInChurchCondition.class, name = "EXTENSION_INTEGRATION_IN_CHURCH_CONDITION"),
        @Type(value = ExtensionIntegrationInUnitCondition.class, name = "EXTENSION_INTEGRATION_IN_UNIT_CONDITION"),
        @Type(value = IdentityCondition.class, name = "IDENTITY"),
        @Type(value = UnitMembershipCondition.class, name = "UNIT_MEMBERSHIP"),
})
public interface SerializableCondition extends Condition, Serializable {

}
