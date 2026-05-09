package br.org.kinflasy.libs.churches.contracts.access_rules;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.kinflasy.libs.churches.dto.access_rules.ChurchRule;
import br.org.kinflasy.libs.churches.dto.access_rules.DepartmentRule;
import br.org.kinflasy.libs.churches.dto.access_rules.UnitRule;
import br.org.kinflasy.libs.churches.dto.access_rules.UserRule;
import dev.openfga.sdk.api.client.model.ClientRelationshipCondition;

@JsonSubTypes({
        @Type(name = "USER", value = UserRule.class),
        @Type(name = "DEPARTMENT", value = DepartmentRule.class),
        @Type(name = "UNIT", value = UnitRule.class),
        @Type(name = "CHURCH", value = ChurchRule.class) })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface AccessRule {

    public AccessCondition getCondition();

    @JsonIgnore
    public String getFgaUser();

    @JsonIgnore
    default Optional<ClientRelationshipCondition> getFgaCondition() {
        return Optional.ofNullable(getCondition())
                .map(AccessCondition::writeCondition);
    }

}
