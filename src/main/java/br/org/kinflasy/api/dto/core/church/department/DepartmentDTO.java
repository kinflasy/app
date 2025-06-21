package br.org.kinflasy.api.dto.core.church.department;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.people_filter.PeopleFilter;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;

public record DepartmentDTO(
        Integer id,
        String name,
        String slug,
        UnitDTO unit,
        DepartmentType type,
        PeopleFilter visibilityFilter) {

    public static @Nullable DepartmentDTO ofNullable(final @Nullable Department department) {
        return (department != null) ? ofNonNull(department) : null;
    }

    public static DepartmentDTO ofNonNull(final Department department) {
        return new DepartmentDTO(department.getId(), department.getName(), department.getSlug(),
                UnitDTO.ofNonNull(department.getUnit()), department.getType(), department.getVisibilityFilter());
    }

}
