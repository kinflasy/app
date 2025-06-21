package br.org.kinflasy.api.dto.core.contact;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.contact.Address;

public record AddressDTO(
        Integer id,
        @Nullable String zip,
        @Nullable String country,
        @Nullable String state,
        @Nullable String city,
        @Nullable String neighborhood,
        @Nullable String street,
        @Nullable String number,
        @Nullable String reference) {

    public static @Nullable AddressDTO ofNullable(final @Nullable Address address) {
        return (address != null) ? ofNonNull(address) : null;
    }

    public static AddressDTO ofNonNull(final Address address) {
        return new AddressDTO(address.getId(), address.getZip(), address.getCountry(), address.getState(),
                address.getCity(), address.getNeighborhood(), address.getStreet(), address.getNumber(),
                address.getReference());
    }

}
