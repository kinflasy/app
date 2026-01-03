package br.org.kinflasy.libs.people_filters.builder.implementations;

import br.org.kinflasy.libs.people_filters.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FilledConditionBuilder implements ReadyConditionBuilder {

    private final Condition condition;

    @Override
    public Condition build() {
        return condition;
    }

}
