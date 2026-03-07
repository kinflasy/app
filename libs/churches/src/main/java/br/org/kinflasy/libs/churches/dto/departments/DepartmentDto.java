package br.org.kinflasy.libs.churches.dto.departments;

import java.util.List;
import java.util.UUID;

import br.org.kinflasy.libs.churches.contracts.access_rules.UnitLevelRule;
import br.org.kinflasy.libs.churches.enums.department.DepartmentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDto {

    private UUID id;
    private UUID unitId;
    private String name;
    private String slug;
    private DepartmentType type;

    @Data
    public static class Rules {
        private List<UnitLevelRule> visibilityRules;
        private List<UnitLevelRule> joinRules;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class WithRules extends DepartmentDto {
        private List<UnitLevelRule> visibilityRules;
        private List<UnitLevelRule> joinRules;
    }

}
