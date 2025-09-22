package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import br.org.kinflasy.libs.people_filters.conditions.logical.AndConditionGroup;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.ReadyConditionBuilder;

public class AndConditionBuilder extends AccumulatorConditionBuilder {

    public AndConditionBuilder(final ConcreteOngoingConditionBuilder concrete) {
        super(concrete);
    }

    @Override
    public ReadyConditionBuilder join() {
        return new FilledConditionBuilder(new AndConditionGroup(conditions));
    }

}
