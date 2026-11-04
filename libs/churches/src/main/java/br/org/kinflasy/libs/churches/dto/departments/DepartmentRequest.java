package br.org.kinflasy.libs.churches.dto.departments;

import java.util.Collections;
import java.util.List;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.enums.department.DepartmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class DepartmentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    @NotNull
    private DepartmentType type;

    @Data
    @Accessors(chain = true)
    public static class Rules {
        private List<AccessRule> visibilityRules = Collections.emptyList();
        private List<AccessRule> joinRules = Collections.emptyList();
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class WithRules extends DepartmentRequest {
        private List<AccessRule> visibilityRules = Collections.emptyList();
        private List<AccessRule> joinRules = Collections.emptyList();
    }

}
