package br.org.kinflasy.libs.churches.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChurchRequest {

    private @NotBlank String name;
    private @NotBlank String slug;
    private String acronym;
    private String phone;
    private @NotBlank @Email String email;

}
