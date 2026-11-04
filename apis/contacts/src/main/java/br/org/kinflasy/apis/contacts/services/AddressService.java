package br.org.kinflasy.apis.contacts.services;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.contacts.converters.AddressConverter;
import br.org.kinflasy.apis.contacts.repositories.AddressRepository;
import br.org.kinflasy.libs.contacts.dto.AddressDto;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressService {

    private static final String NOT_FOUND_MESSAGE = "Endereço não encontrado.";

    private final AddressRepository repository;
    private final AddressConverter converter;

    /*
     * ACESSO PÚBLICO (inclusive deslogado, nesse caso)
     */

    @Transactional
    public AddressDto create(final AddressRequest form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    @Transactional
    public AddressDto create(final AddressRequest form, final UUID createdBy) {
        final var entity = converter.toEntity(form);
        entity.setCreatedBy(createdBy);
        repository.save(entity);
        return converter.toDto(entity);
    }

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('address', #id, 'can_view', 'user', principal.id)")
    public AddressDto findById(final UUID id) {
        return repository.findById(id).map(converter::toDto)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    @PreAuthorize("@fga.check('address', #id, 'can_edit', 'user', principal.id)")
    public AddressDto update(final UUID id, final AddressRequest form) {
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var updated = converter.toEntity(form, original);

        repository.save(updated);
        return converter.toDto(updated);
    }

    @Transactional
    @PreAuthorize("@fga.check('address', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
