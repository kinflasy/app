package br.org.kinflasy.libs.people_filters.utils.builder.contracts;

import java.util.UUID;

import org.springframework.boot.test.context.SpringBootTest;

import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;

@SpringBootTest
public class ConditionBuilderTest {

    void basicTest(SingleConditionBuilder thePerson) {

        thePerson
                .matchesAllConditions(and -> and
                        .is(PersonCharacteristic.ADULT)
                        .is(PersonCharacteristic.FEMALE)
                        .not(not -> not.is(PersonCharacteristic.INACTIVE))
                        .matchesAnyCondition(or -> or.isIntegrantOfDepartment(UUID.randomUUID())))
                .build();

    }

}
