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

@AllArgsConstructor
public enum PersonCharacteristic implements Predicate<PersonDto> {

    EVERYBODY(person -> true),
    MALE(person -> person.getGender() == Gender.MALE),
    FEMALE(person -> person.getGender() == Gender.FEMALE),
    ADULT(person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() >= PeopleConstants.ADULT_AGE),
    MINOR(ADULT.negate()),
    USER(UserDto.class::isInstance),
    INACTIVE(InactivePersonDto.class::isInstance);

    private final Predicate<PersonDto> predicate;

    @Override
    public boolean test(final PersonDto person) {
        return predicate.test(person);
    }

}
