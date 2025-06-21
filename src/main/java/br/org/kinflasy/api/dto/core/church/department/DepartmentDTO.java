package br.org.kinflasy.api.dto.core.church.department;


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

    public static DepartmentDTO ofNullable(final Department department) {
        return (department != null) ? ofNonNull(department) : null;
    }

    public static DepartmentDTO ofNonNull(final Department department) {
        return new DepartmentDTO(department.getId(), department.getName(), department.getSlug(),
                UnitDTO.ofNonNull(department.getUnit()), department.getType(), department.getVisibilityFilter());
    }

}
