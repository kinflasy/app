package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ConditionBuilder {

    private static final ConcreteOngoingConditionBuilder CONCRETE = new ConcreteOngoingConditionBuilder();

    public static StarterConditionBuilder thePerson() {
        return new StarterConditionBuilder(CONCRETE);
    }

}
