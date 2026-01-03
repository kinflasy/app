package br.org.kinflasy.libs.base_conditions.dto;

import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ConditionRequest {

    private @NotNull Condition condition;

}
