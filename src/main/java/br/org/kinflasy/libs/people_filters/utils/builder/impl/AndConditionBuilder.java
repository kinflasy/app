package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import br.org.kinflasy.libs.people_filters.contracts.logical.AndContractGroup;
import br.org.kinflasy.libs.people_filters.utils.builder.contracts.ReadyConditionBuilder;

public class AndConditionBuilder extends AccumulatorConditionBuilder {

    public AndConditionBuilder(final ConcreteOngoingConditionBuilder concrete) {
        super(concrete);
    }

    @Override
    public ReadyConditionBuilder join() {
        return new FilledConditionBuilder(new AndContractGroup(conditions));
    }

}
