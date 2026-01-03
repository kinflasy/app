package br.org.kinflasy.libs.churches.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChurchRequest {

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Starter extends ChurchRequest {
        private @NotNull UnitRequest unit;
    }

    private @NotBlank String name;
    private @NotBlank String slug;
    private String acronym;
    private String phone;
    private @NotBlank @Email String email;

}
