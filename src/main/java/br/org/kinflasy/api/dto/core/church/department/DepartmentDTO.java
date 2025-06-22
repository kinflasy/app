package br.org.kinflasy.api.dto.core.church.department;

import java.util.UUID;

import br.org.kinflasy.api.apis.churches.entities.department.Department;
import br.org.kinflasy.api.apis.people_filters.entities.PeopleFilter;
import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.libs.churches.enums.department.DepartmentType;

public record DepartmentDTO(
        UUID id,
        String name,
        String slug,
        UnitDTO unit,
        DepartmentType type,
        PeopleFilter visibilityFilter) {

    public static DepartmentDTO ofNullable(final Department department) {
        return (department != null) ? ofNonNull(department) : null;
    }

    public static DepartmentDTO ofNonNull(final Department department) {
        return new DepartmentDTO(department.getId(), department.getName(), department.getSlug(),
                UnitDTO.ofNonNull(department.getUnit()), department.getType(), department.getVisibilityFilter());
    }

}
