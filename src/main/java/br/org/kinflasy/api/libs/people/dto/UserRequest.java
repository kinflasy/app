package br.org.kinflasy.api.libs.people.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public interface UserRequest {

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public abstract class Create extends PersonRequest.Create {
        private @NotBlank String username;
        private @NotBlank String email;
        private LocalDateTime emailVerifiedAt;
        private @NotBlank String password;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public abstract class Update extends Create {
        private @NotNull UUID id;
    }

}
