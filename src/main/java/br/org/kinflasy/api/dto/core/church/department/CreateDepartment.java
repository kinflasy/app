package br.org.kinflasy.api.dto.core.church.department;


import br.org.kinflasy.api.apis.churches.entities.department.Department;
import br.org.kinflasy.api.apis.people_filters.entities.StaticPeopleFilter;
import br.org.kinflasy.api.libs.churches.enums.department.DepartmentType;
import br.org.kinflasy.api.libs.people_filters.enums.PersonCharacteristic;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateDepartment {

    private String name;
    private String slug;
    private DepartmentType type;

    public Department update(final Department department) {
        department.setName(name);
        department.setSlug(slug);
        department.setType(type);
        department.setVisibilityFilter(new StaticPeopleFilter(PersonCharacteristic.EVERYBODY));

        return department;
    }

    public Department toDepartment() {
        return update(new Department());
    }

}
