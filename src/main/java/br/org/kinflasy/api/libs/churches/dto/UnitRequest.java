package br.org.kinflasy.api.libs.churches.dto;

import java.util.UUID;

import br.org.kinflasy.api.libs.churches.enums.UnitType;
import br.org.kinflasy.api.libs.contacts.dto.AddressRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public interface UnitRequest {

    @Data
    @NoArgsConstructor
    public class Create {
        private @NotBlank String name;
        private @NotBlank String slug;
        private @NotBlank String phone;
        private @NotBlank String email;
        private @NotNull UnitType type;
        private @NotNull AddressRequest.Create address;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public class Update extends Create {
        private @NotNull UUID id;
    }

}
