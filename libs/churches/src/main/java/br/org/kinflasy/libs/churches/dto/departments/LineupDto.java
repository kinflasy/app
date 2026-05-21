package br.org.kinflasy.libs.churches.dto.departments;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonSubTypes({
        @Type(name = "DEPARTMENT", value = DepartmentLineupDto.class),
        @Type(name = "UNIT", value = UnitLineupDto.class) })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public class LineupDto {

    private UUID id;
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
