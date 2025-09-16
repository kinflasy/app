package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import br.org.kinflasy.libs.people_filters.conditions.OrConditionGroup;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.ReadyConditionBuilder;

public class OrConditionBuilder extends AccumulatorConditionBuilder {

    public OrConditionBuilder(final ConcreteOngoingConditionBuilder concrete) {
        super(concrete);
    }

    @Override
    public ReadyConditionBuilder join() {
        return new FilledConditionBuilder(new OrConditionGroup(conditions));
    }

}
