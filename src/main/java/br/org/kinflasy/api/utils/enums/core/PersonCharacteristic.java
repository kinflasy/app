package br.org.kinflasy.api.utils.enums.core;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;

import br.org.kinflasy.api.entities.core.InactivePerson;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.User;

public enum PersonCharacteristic {

    EVERYBODY(person -> true),
    MALE(person -> person.getGender() == Gender.MALE),
    FEMALE(person -> person.getGender() == Gender.FEMALE),
    ADULT(person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() >= Person.ADULT_AGE),
    MINOR(person -> !(ADULT.getFilter().test(person))),
    USER(person -> person instanceof User),
    INACTIVE(person -> person instanceof InactivePerson);

    private final Predicate<Person> filter;

    private PersonCharacteristic(final Predicate<Person> filter) {
        this.filter = filter;
    }

    public Predicate<Person> getFilter() {
        return filter;
    }

}
