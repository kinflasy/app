package br.org.kinflasy.libs.churches.dto.departments;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineupDto {

    private UUID id;
    private UUID departmentId;
    private String name;

    @Data
    @NoArgsConstructor
    public static class Item {
        private UUID id;
        private UUID lineupId;
        private UUID abilityId;
        private String description;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class WithItems extends LineupDto {
        private List<Item> items;
    }

}
