package br.org.kinflasy.api.libs.churches.dto.departments;

import java.util.UUID;

import br.org.kinflasy.api.apis.people_filters.entities.PeopleFilter;
import br.org.kinflasy.api.libs.churches.dto.UnitDto;
import br.org.kinflasy.api.libs.churches.enums.department.DepartmentType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentDto {

    private UUID id;
    private String name;
    private String slug;
    private UnitDto unit;
    private DepartmentType type;
    private PeopleFilter visibilityFilter;

}
