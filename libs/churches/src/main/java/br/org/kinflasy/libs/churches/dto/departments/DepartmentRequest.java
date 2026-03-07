package br.org.kinflasy.libs.churches.dto.departments;

import java.util.List;

import br.org.kinflasy.libs.churches.contracts.access_rules.UnitLevelRule;
import br.org.kinflasy.libs.churches.enums.department.DepartmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentRequest {

    private @NotBlank String name;
    private @NotBlank String slug;
    private @NotNull DepartmentType type;

    @Data
    public static class Rules {
        private List<UnitLevelRule> visibilityRules;
        private List<UnitLevelRule> joinRules;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class WithRules extends DepartmentRequest {
        private List<UnitLevelRule> visibilityRules;
        private List<UnitLevelRule> joinRules;
    }

}
