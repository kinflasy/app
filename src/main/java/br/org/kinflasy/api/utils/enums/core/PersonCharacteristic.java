package br.org.kinflasy.api.utils.enums.core;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;

import br.org.kinflasy.api.entities.core.InactivePerson;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonCharacteristic {

    EVERYBODY(person -> true),
    MALE(person -> person.getGender() == Gender.MALE),
    FEMALE(person -> person.getGender() == Gender.FEMALE),
    ADULT(person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() >= Person.ADULT_AGE),
    MINOR(person -> !(ADULT.getPredicate().test(person))),
    USER(person -> person instanceof User),
    INACTIVE(person -> person instanceof InactivePerson);

    private final Predicate<Person> predicate;

}
