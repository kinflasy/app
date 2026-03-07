package br.org.kinflasy.libs.churches.dto.departments;

import java.util.Collections;
import java.util.List;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.enums.department.DepartmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    @NotNull
    private DepartmentType type;

    @Data
    public static class Rules {
        @NotEmpty
        private List<AccessRule> visibilityRules = Collections.emptyList();

        private List<AccessRule> joinRules = Collections.emptyList();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class WithRules extends DepartmentRequest {
        @NotEmpty
        private List<AccessRule> visibilityRules = Collections.emptyList();

        private List<AccessRule> joinRules = Collections.emptyList();
    }

}
