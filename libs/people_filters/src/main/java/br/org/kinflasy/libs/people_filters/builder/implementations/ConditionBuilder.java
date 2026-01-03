package br.org.kinflasy.libs.people_filters.builder.implementations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ConditionBuilder {

    public static StarterConditionBuilder thePerson() {
        return new StarterConditionBuilder(new ConcreteOngoingConditionBuilder());
    }

}
