package br.org.kinflasy.api.libs.people.dto;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.api.libs.contacts.dto.AddressRequest;
import br.org.kinflasy.api.libs.people.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public interface PersonRequest {

    @Data
    @NoArgsConstructor
    public abstract class Create {
        private @NotBlank String fullName;
        private String nickname;
        private @NotNull Gender gender;
        private @NotNull LocalDate birthDate;
        private String phone;
        private AddressRequest.Create address;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public abstract class Update extends Create {
        private @NotNull UUID id;
        private AddressRequest.Update address;
    }

}
