package br.org.kinflasy.libs.people_filters.enums;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;

import br.org.kinflasy.libs.people.PeopleConstants;
import br.org.kinflasy.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonCharacteristic {

    EVERYBODY(person -> true),
    MALE(person -> person.getGender() == Gender.MALE),
    FEMALE(person -> person.getGender() == Gender.FEMALE),
    ADULT(person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() >= PeopleConstants.ADULT_AGE),
    MINOR(person -> !(ADULT.getPredicate().test(person))),
    USER(UserDto.class::isInstance),
    INACTIVE(InactivePersonDto.class::isInstance);

    private final Predicate<PersonDto> predicate;

}
