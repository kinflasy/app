package br.org.kinflasy.libs.churches.contracts.access_rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import br.org.kinflasy.libs.churches.dto.access_rules.CharacteristicCondition;
import dev.openfga.sdk.api.client.model.ClientRelationshipCondition;

@JsonSubTypes({
        @Type(name = "CHARACTERISTIC", value = CharacteristicCondition.class),
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface AccessCondition {

    ClientRelationshipCondition writeCondition();

}
