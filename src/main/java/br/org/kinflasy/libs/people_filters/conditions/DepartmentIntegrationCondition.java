package br.org.kinflasy.libs.people_filters.conditions;

import java.util.UUID;

import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DepartmentIntegrationCondition extends Condition {

    private final UUID departmentId;

    private final IntegrationType type;

    @Override
    public boolean test(final PersonDto person) {
        // TODO implementar filtro
        return false;
    }

}
