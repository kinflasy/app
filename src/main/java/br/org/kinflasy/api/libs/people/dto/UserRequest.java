package br.org.kinflasy.api.libs.people.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public interface UserRequest {

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public class Create extends PersonRequest.Create {
        private @NotBlank String username;
        private @NotBlank String email;
        private LocalDateTime emailVerifiedAt;
        private @NotBlank String password;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public class Update extends PersonRequest.Update {
        private String username;
        private String email;
        private LocalDateTime emailVerifiedAt;
        private String password;
    }

}
