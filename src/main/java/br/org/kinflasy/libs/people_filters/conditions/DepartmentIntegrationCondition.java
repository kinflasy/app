package br.org.kinflasy.libs.people_filters.conditions;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DepartmentIntegrationCondition extends Condition {

    @JsonIgnore
    private final ConditionTester tester;

    private final UUID departmentId;

    private final IntegrationType type;

    @Override
    public boolean test(final PersonDto person) {
        return tester.isIntegrantOfDepartment(this, person);
    }

}
