package br.org.kinflasy.libs.churches.dto;

import br.org.kinflasy.libs.churches.enums.UnitType;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitRequest {

    private @NotBlank String name;
    private @NotBlank String slug;
    private @NotBlank String phone;
    private @NotBlank @Email String email;
    private @NotNull UnitType type;
    private @NotNull AddressRequest address;

}
