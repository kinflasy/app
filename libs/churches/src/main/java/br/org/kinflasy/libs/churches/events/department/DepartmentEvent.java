package br.org.kinflasy.libs.churches.events.department;

import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import lombok.Data;

@Data
public abstract class DepartmentEvent {

    private final DepartmentDto department;

    public static class Created extends DepartmentEvent {
        public Created(final DepartmentDto department) {
            super(department);
        }
    }

}
