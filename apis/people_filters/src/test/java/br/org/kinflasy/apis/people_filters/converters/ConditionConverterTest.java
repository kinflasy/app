package br.org.kinflasy.apis.people_filters.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.kinflasy.libs.people_filters.builder.implementations.ConditionBuilder;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.val;

@SpringBootTest
class ConditionConverterTest {

    @Autowired
    private StoredConditionConverter converter;

    @Test
    void example() {
        val condition = ConditionBuilder.thePerson()
                .matchesAllConditions(thePerson -> thePerson
                        .is(PersonCharacteristic.FEMALE)
                        .is(PersonCharacteristic.ADULT)
                        .not(not -> not.is(PersonCharacteristic.INACTIVE)))
                .build();

        val entity = converter.toEntity(condition);

        val dto = converter.toDto(entity);

        assertEquals(condition, dto);
    }

}
