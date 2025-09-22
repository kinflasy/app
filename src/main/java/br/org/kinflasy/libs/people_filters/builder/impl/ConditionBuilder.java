package br.org.kinflasy.libs.people_filters.builder.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ConditionBuilder {

    public static StarterConditionBuilder thePerson() {
        return new StarterConditionBuilder(new ConcreteOngoingConditionBuilder());
    }

}
