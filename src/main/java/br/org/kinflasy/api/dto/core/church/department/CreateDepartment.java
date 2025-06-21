package br.org.kinflasy.api.dto.core.church.department;


import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.people_filter.StaticPeopleFilter;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;
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
