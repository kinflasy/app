package br.org.kinflasy.libs.churches.dto.departments;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
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
    private UUID profileImageId;
    private UUID coverImageId;

    @Data
    public static class Rules {
        private List<AccessRule> visibilityRules = Collections.emptyList();
        private List<AccessRule> joinRules = Collections.emptyList();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class WithRules extends DepartmentDto {
        private List<AccessRule> visibilityRules = Collections.emptyList();
        private List<AccessRule> joinRules = Collections.emptyList();
    }

}
