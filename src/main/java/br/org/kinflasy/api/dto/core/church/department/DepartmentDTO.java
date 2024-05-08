package br.org.kinflasy.api.dto.core.church.department;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.peoplefilter.PeopleFilter;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;

public record DepartmentDTO(
        @NonNull Integer id,
        @NonNull String name,
        @NonNull String slug,
        @NonNull UnitDTO unit,
        @NonNull DepartmentType type,
        @NonNull PeopleFilter visibilityFilter) {

    public static @Nullable DepartmentDTO ofNullable(final @Nullable Department department) {
        return (department != null) ? ofNonNull(department) : null;
    }

    public static @NonNull DepartmentDTO ofNonNull(final @NonNull Department department) {
        return new DepartmentDTO(department.getId(), department.getName(), department.getSlug(),
                UnitDTO.ofNonNull(department.getUnit()), department.getType(), department.getVisibilityFilter());
    }

}
