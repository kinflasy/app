package br.org.kinflasy.api.libs.people.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public interface InactivePersonRequest {

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public class Create extends PersonRequest.Create {
        private String email;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public class Update extends PersonRequest.Update {
        private String email;
    }

}
