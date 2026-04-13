package br.org.kinflasy.libs.contacts.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkRequest {

    @NotBlank
    private String label;

    @NotBlank
    private String url;

}
