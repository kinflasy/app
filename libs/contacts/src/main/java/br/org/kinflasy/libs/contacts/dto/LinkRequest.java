package br.org.kinflasy.libs.contacts.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class LinkRequest {

    @NotBlank
    private String label;

    @NotBlank
    private String url;

}
