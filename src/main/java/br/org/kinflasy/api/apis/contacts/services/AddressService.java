package br.org.kinflasy.api.apis.contacts.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.contacts.converters.AddressConverter;
import br.org.kinflasy.api.apis.contacts.repositories.AddressRepository;
import br.org.kinflasy.api.libs.contacts.dto.AddressDto;
import br.org.kinflasy.api.libs.contacts.dto.AddressRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository repository;
    private final AddressConverter converter;

    public List<AddressDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public AddressDto create(final AddressRequest.Create form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public AddressDto findById(final UUID id) {
        return repository.findById(id).map(converter::toDto).orElseThrow();
    }

    public AddressDto update(final AddressRequest.Update form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
