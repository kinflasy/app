package br.org.kinflasy.libs.churches.dto.departments;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineupRequest {

    @NotNull
    private UUID departmentId;

    @NotBlank
    private String name;

    @Data
    @NoArgsConstructor
    public static class Item {
        @NotNull
        private UUID lineupId;

        @NotNull
        private UUID abilityId;

        @NotBlank
        private String description;
    }

}
