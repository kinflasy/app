package br.org.kinflasy.api.utils.enums.core;

import java.util.function.Function;

import br.org.kinflasy.api.entities.core.Person;

public enum PersonCharacteristic {

    EVERYBODY(person -> true),
    MALE(person -> person.getGender() == Gender.MALE),
    FEMALE(person -> person.getGender() == Gender.FEMALE);

    private final Function<Person, Boolean> filter;

    private PersonCharacteristic(final Function<Person, Boolean> filter) {
        this.filter = filter;
    }

    public Function<Person, Boolean> getFilter() {
        return filter;
    }

}
