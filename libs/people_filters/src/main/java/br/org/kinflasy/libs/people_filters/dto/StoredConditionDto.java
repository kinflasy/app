package br.org.kinflasy.libs.people_filters.dto;

import java.util.UUID;

import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.Value;

@Value
public class StoredConditionDto<C extends Condition> {

    private UUID id;
    private C condition;

}
