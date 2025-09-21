package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import br.org.kinflasy.libs.people_filters.contracts.structure.ConditionContract;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.ReadyConditionBuilder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FilledConditionBuilder implements ReadyConditionBuilder {

    private final ConditionContract condition;

    @Override
    public ConditionContract build() {
        return condition;
    }

}
