package br.org.kinflasy.libs.churches.dto.lineups;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LineupRequest {

    @NotBlank
    private String name;

    @Data
    @NoArgsConstructor
    public static class Item {
        @NotNull
        private UUID roleId;

        @NotBlank
        private String description;
    }

    @Data
    @NoArgsConstructor
    public static class UpdateItem {
        @NotBlank
        private String description;
    }

}
