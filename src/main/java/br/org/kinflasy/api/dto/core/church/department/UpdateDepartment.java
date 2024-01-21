package br.org.kinflasy.api.dto.core.church.department;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;

public record UpdateDepartment(
        @Nullable String name,
        @Nullable String slug,
        @Nullable DepartmentType type) {

    public @NonNull Department update(final @NonNull Department department) {

        if (name != null) {
            department.setName(name);
        }

        if (slug != null) {
            department.setSlug(slug);
        }

        if (type != null) {
            department.setType(type);
        }

        return department;
    }


}
