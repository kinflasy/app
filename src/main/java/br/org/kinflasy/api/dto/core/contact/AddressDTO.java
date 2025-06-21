package br.org.kinflasy.api.dto.core.contact;


import java.util.UUID;

import br.org.kinflasy.api.entities.core.contact.Address;

public record AddressDTO(
        UUID id,
        String zip,
        String country,
        String state,
        String city,
        String neighborhood,
        String street,
        String number,
        String reference) {

    public static AddressDTO ofNullable(final Address address) {
        return (address != null) ? ofNonNull(address) : null;
    }

    public static AddressDTO ofNonNull(final Address address) {
        return new AddressDTO(address.getId(), address.getZip(), address.getCountry(), address.getState(),
                address.getCity(), address.getNeighborhood(), address.getStreet(), address.getNumber(),
                address.getReference());
    }

}
