package br.org.kinflasy.api.libs.people.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public interface InactivePersonRequest {

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public abstract class Create extends PersonRequest.Create {
        private String email;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public abstract class Update extends Create {
        private @NotNull UUID id;
    }

}
