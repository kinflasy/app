package br.org.kinflasy.api.dto.core.contact;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.contact.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateAddress {

    @Nullable
    private String zip;

    @Nullable
    private String country;

    @Nullable
    private String state;

    @Nullable
    private String city;

    @Nullable
    private String neighborhood;

    @Nullable
    private String street;

    @Nullable
    private String number;

    @Nullable
    private String reference;
    
    public Address toAddress() {
        final var address = new Address();

        address.setZip(zip);
        address.setCountry(country);
        address.setState(state);
        address.setCity(city);
        address.setNeighborhood(neighborhood);
        address.setStreet(street);
        address.setNumber(number);
        address.setReference(reference);

        return address;
    }

}
