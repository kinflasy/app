package br.org.kinflasy.libs.people_filters.builder.contracts;

public interface AccumulatedConditionBuilder extends OngoingConditionBuilder<AccumulatedConditionBuilder> {

    ReadyConditionBuilder join();

}
