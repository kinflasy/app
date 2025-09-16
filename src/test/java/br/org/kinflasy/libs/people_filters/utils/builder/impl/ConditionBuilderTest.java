package br.org.kinflasy.libs.people_filters.utils.builder.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.enums.Gender;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;

@SpringBootTest
class ConditionBuilderTest {

    private static final PersonDto MARY = new UserDto()
            .setGender(Gender.FEMALE)
            .setBirthDate(LocalDate.of(2000, 1, 1));

    @Test
    void basicTest() {

        final var condition = ConditionBuilder.thePerson()
                .matchesAllConditions(and -> and
                        .is(PersonCharacteristic.ADULT)
                        .is(PersonCharacteristic.FEMALE)
                        .is(PersonCharacteristic.USER)
                        .not(not -> not.matchesAnyCondition(or -> or.isIntegrantOfDepartment(UUID.randomUUID(), null))))
                .build();

        assertTrue(condition.test(MARY));

    }

}
