package br.org.kinflasy.api.dto.core.church.department;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.peopleFilter.PeopleFilter;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;

public record CleanDepartmentDTO(
        @NonNull Integer id,
        @NonNull String name,
        @NonNull String slug,
        @NonNull DepartmentType type,
        @NonNull PeopleFilter visibilityFilter) {

    public static @Nullable CleanDepartmentDTO ofNullable(final @Nullable Department department) {
        return (department != null) ? ofNonNull(department) : null;
    }

    public static @NonNull CleanDepartmentDTO ofNonNull(final @NonNull Department department) {
        return new CleanDepartmentDTO(department.getId(), department.getName(), department.getSlug(),
                department.getType(), department.getVisibilityFilter());
    }

}
