package br.org.kinflasy.api.apis.contacts.converters;

import br.org.kinflasy.api.apis.contacts.entities.Address;
import br.org.kinflasy.api.libs.contacts.dto.AddressDto;
import br.org.kinflasy.api.libs.contacts.dto.AddressRequest;

public interface AddressConverter {

    public static Address toEntity(final AddressDto dto) {
        final var entity = new Address();

        entity.setId(dto.getId());
        entity.setZip(dto.getZip());
        entity.setCountry(dto.getCountry());
        entity.setState(dto.getState());
        entity.setCity(dto.getCity());
        entity.setNeighborhood(dto.getNeighborhood());
        entity.setStreet(dto.getStreet());
        entity.setNumber(dto.getNumber());
        entity.setReference(dto.getReference());

        return entity;
    }

    public static Address toEntity(final AddressRequest.Create request) {
        final var entity = new Address();

        entity.setZip(request.getZip());
        entity.setCountry(request.getCountry());
        entity.setState(request.getState());
        entity.setCity(request.getCity());
        entity.setNeighborhood(request.getNeighborhood());
        entity.setStreet(request.getStreet());
        entity.setNumber(request.getNumber());
        entity.setReference(request.getReference());

        return entity;
    }

    public static Address toEntity(final AddressRequest.Update request) {
        final var entity = toEntity((AddressRequest.Create) request);
        entity.setId(request.getId());

        return entity;
    }

    public static AddressDto toDto(final Address entity) {
        return new AddressDto()
                .setId(entity.getId())
                .setZip(entity.getZip())
                .setCountry(entity.getCountry())
                .setState(entity.getState())
                .setCity(entity.getCity())
                .setNeighborhood(entity.getNeighborhood())
                .setStreet(entity.getStreet())
                .setNumber(entity.getNumber())
                .setReference(entity.getReference());
    }

}
