package br.org.kinflasy.libs.people_filters.builder.implementations;

import br.org.kinflasy.libs.people_filters.builder.contracts.ReadyConditionBuilder;
import br.org.kinflasy.libs.people_filters.conditions.logical.OrConditionGroup;

public class OrConditionBuilder extends AccumulatorConditionBuilder {

    public OrConditionBuilder(final ConcreteOngoingConditionBuilder concrete) {
        super(concrete);
    }

    @Override
    public ReadyConditionBuilder join() {
        final var conditionGroup = new OrConditionGroup();
        conditionGroup.getConditions().addAll(conditions);

        return new FilledConditionBuilder(conditionGroup);
    }

}
