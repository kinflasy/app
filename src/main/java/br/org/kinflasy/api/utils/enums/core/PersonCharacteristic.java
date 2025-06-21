package br.org.kinflasy.api.utils.enums.core;

import java.util.function.Predicate;

import br.org.kinflasy.api.entities.core.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonCharacteristic {

    EVERYBODY(person -> true),
    MALE(person -> person.getGender() == Gender.MALE),
    FEMALE(person -> person.getGender() == Gender.FEMALE);

    private final Predicate<Person> predicate;

}
