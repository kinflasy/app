package br.org.kinflasy.api.dto.core.church.department;


import java.util.UUID;

import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.people_filter.PeopleFilter;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;

public record CleanDepartmentDTO(
        UUID id,
        String name,
        String slug,
        DepartmentType type,
        PeopleFilter visibilityFilter) {

    public static CleanDepartmentDTO ofNullable(final Department department) {
        return (department != null) ? ofNonNull(department) : null;
    }

    public static CleanDepartmentDTO ofNonNull(final Department department) {
        return new CleanDepartmentDTO(department.getId(), department.getName(), department.getSlug(),
                department.getType(), department.getVisibilityFilter());
    }

}
