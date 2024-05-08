package br.org.kinflasy.api.entities.core.peoplefilter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.peoplefilter.builder.PeopleFilterBuilder;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;
import br.org.kinflasy.api.utils.enums.core.church.department.IntegrationType;
import br.org.kinflasy.api.utils.enums.core.church.membership.Status;

@SpringBootTest
public class PeopleFilterBuilderTest {

    @Test
    void creative() {
        final var personX = new User();
        final var churchX = new Church();
        final var unitX = new Unit();
        final var departmentX = new Department();

        final var builder = PeopleFilterBuilder
                .thePerson()
                .isMemberOf(unitX, Status.CONGREGATED, Status.MEMBER, Status.VISITOR)
                .andMatchesAll(
                        person -> person
                                .is(personX)
                                .is(PersonCharacteristic.ADULT)
                                .isMemberOf(churchX, Status.MEMBER))
                .allThisOrMatchesAll(list -> list
                        .isMemberOf(unitX, Status.CONGREGATED)
                        .isIntegrantOf(departmentX, IntegrationType.LEADER));

        System.out.println(builder);

        final var result = builder.build();

        System.out.println(result);

        assertDoesNotThrow(() -> builder.build());

    }

}
