package br.org.kinflasy.libs.people_filters.builder.impl;

import br.org.kinflasy.libs.people_filters.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.conditions.logical.OrConditionGroup;

public class OrConditionBuilder extends AccumulatorConditionBuilder {

    public OrConditionBuilder(final ConcreteOngoingConditionBuilder concrete) {
        super(concrete);
    }

    @Override
    public ReadyConditionBuilder join() {
        return new FilledConditionBuilder(new OrConditionGroup(conditions));
    }

}
