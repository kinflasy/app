package br.org.kinflasy.api.dto.core.church.department;


import br.org.kinflasy.api.apis.churches.entities.department.Department;
import br.org.kinflasy.api.libs.churches.enums.department.DepartmentType;

public record UpdateDepartment(
        String name,
        String slug,
        DepartmentType type) {

    public Department update(final Department department) {

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
