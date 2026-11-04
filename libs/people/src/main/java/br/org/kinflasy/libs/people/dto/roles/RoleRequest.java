package br.org.kinflasy.libs.people.dto.roles;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleRequest {

    @NotBlank
    private String name;

}
