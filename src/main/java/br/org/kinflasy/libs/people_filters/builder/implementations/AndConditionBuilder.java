package br.org.kinflasy.libs.people_filters.builder.implementations;

import br.org.kinflasy.libs.people_filters.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.conditions.logical.AndConditionGroup;

public class AndConditionBuilder extends AccumulatorConditionBuilder {

    public AndConditionBuilder(final ConcreteOngoingConditionBuilder concrete) {
        super(concrete);
    }

    @Override
    public ReadyConditionBuilder join() {
        return new FilledConditionBuilder(new AndConditionGroup(conditions));
    }

}
