package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import br.org.kinflasy.libs.people_filters.conditions.ConditionTester;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ConditionBuilder {

    public static StarterConditionBuilder thePerson(final ConditionTester tester) {
        return new StarterConditionBuilder(new ConcreteOngoingConditionBuilder(tester));
    }

}
