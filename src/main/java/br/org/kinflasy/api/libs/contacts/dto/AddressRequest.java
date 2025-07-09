package br.org.kinflasy.api.libs.contacts.dto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public interface AddressRequest {

    @Data
    @NoArgsConstructor
    public class Create {
        private String zip;
        private String country;
        private String state;
        private String city;
        private String neighborhood;
        private String street;
        private String number;
        private String reference;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public class Update extends Create {
        private UUID id;
    }

}
