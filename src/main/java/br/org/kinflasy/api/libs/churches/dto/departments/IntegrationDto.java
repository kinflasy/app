package br.org.kinflasy.api.libs.churches.dto.departments;

import java.util.UUID;

import br.org.kinflasy.api.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.api.libs.people.dto.PersonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IntegrationDto {

    private UUID id;
    private DepartmentDto department;
    private PersonDto person;
    private IntegrationType type;

}
