package br.org.kinflasy.libs.contacts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class AddressRequest {

    private String zip;
    private String country;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String number;
    private String complement;
    private String reference;

}
