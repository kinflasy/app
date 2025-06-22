package br.org.kinflasy.api.libs.people_filters.enums;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;

import br.org.kinflasy.api.apis.people.entities.InactivePerson;
import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.libs.people.enums.Gender;
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
    USER(User.class::isInstance),
    INACTIVE(InactivePerson.class::isInstance);

    private final Predicate<Person> predicate;

}
