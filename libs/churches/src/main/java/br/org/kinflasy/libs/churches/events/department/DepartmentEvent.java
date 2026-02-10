package br.org.kinflasy.libs.churches.events.department;

import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import lombok.Data;

public interface DepartmentEvent {

    public DepartmentDto getDepartment();

    @Data
    public static class Created implements DepartmentEvent {
        private final DepartmentDto department;
    }

}
