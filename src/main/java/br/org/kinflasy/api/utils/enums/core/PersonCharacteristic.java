package br.org.kinflasy.api.utils.enums.core;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.InactivePerson;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.User;

public enum PersonCharacteristic {

    EVERYBODY(person -> true),
    MALE(person -> person.getGender() == Gender.MALE),
    FEMALE(person -> person.getGender() == Gender.FEMALE),
    ADULT(person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() >= Person.ADULT_AGE),
    MINOR(person -> !(ADULT.getFilter().apply(person))),
    USER(person -> person instanceof User),
    INACTIVE(person -> person instanceof InactivePerson);

    private final @NonNull Function<Person, Boolean> filter;

    private PersonCharacteristic(final @NonNull Function<Person, Boolean> filter) {
        this.filter = filter;
    }

    public @NonNull Function<Person, Boolean> getFilter() {
        return filter;
    }

}
