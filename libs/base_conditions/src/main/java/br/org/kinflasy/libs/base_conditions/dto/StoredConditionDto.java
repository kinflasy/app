package br.org.kinflasy.libs.base_conditions.dto;

import java.util.UUID;

import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import lombok.Value;

@Value
public class StoredConditionDto<C extends Condition> {

    private UUID id;
    private C condition;

}
