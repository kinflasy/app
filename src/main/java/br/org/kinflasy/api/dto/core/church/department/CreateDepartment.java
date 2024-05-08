package br.org.kinflasy.api.dto.core.church.department;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.peoplefilter.StaticPeopleFilter;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;
import br.org.kinflasy.api.utils.enums.core.church.department.DepartmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateDepartment {

    private @NonNull String name;
    private @NonNull String slug;
    private @NonNull DepartmentType type;

    public @NonNull Department update(final @NonNull Department department) {
        department.setName(name);
        department.setSlug(slug);
        department.setType(type);
        department.setVisibilityFilter(new StaticPeopleFilter(PersonCharacteristic.EVERYBODY));

        return department;
    }

    public @NonNull Department toDepartment() {
        return update(new Department());
    }

}
