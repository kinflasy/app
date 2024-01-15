package br.org.kinflasy.api.utils.enums.core;

import java.util.function.Function;

import br.org.kinflasy.api.entities.core.User;

public enum StaticPeopleFilterMode {

    EVERYBODY(user -> true),
    MALE(user -> user.getPerson().getGender() == Gender.MALE),
    FEMALE(user -> user.getPerson().getGender() == Gender.FEMALE);

    private final Function<User, Boolean> filter;

    private StaticPeopleFilterMode(final Function<User, Boolean> filter) {
        this.filter = filter;
    }

    public Function<User, Boolean> getFilter() {
        return filter;
    }

}
