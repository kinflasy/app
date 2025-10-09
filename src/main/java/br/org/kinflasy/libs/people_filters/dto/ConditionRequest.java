package br.org.kinflasy.libs.people_filters.dto;

import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ConditionRequest {

    private @NotNull Condition condition;

}
