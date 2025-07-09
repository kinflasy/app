package br.org.kinflasy.api.libs.churches.dto.departments;

import java.util.UUID;

import br.org.kinflasy.api.libs.churches.enums.department.DepartmentType;
import br.org.kinflasy.api.libs.contacts.dto.AddressRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public interface DepartmentRequest {

    @Data
    @NoArgsConstructor
    public class Create {
        private @NotBlank String name;
        private @NotBlank String slug;
        private @NotNull DepartmentType type;
        private @NotNull AddressRequest.Create address;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public class Update extends Create {
        private @NotNull UUID id;
    }

}
