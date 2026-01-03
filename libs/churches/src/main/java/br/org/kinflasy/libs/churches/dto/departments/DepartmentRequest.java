package br.org.kinflasy.libs.churches.dto.departments;

import br.org.kinflasy.libs.base_conditions.dto.ConditionRequest;
import br.org.kinflasy.libs.churches.enums.department.DepartmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentRequest {

    private @NotBlank String name;
    private @NotBlank String slug;
    private @NotNull DepartmentType type;
    private ConditionRequest visibility;

}
