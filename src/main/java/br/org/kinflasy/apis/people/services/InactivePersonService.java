package br.org.kinflasy.apis.people.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.clients.AddressClient;
import br.org.kinflasy.apis.people.converters.InactivePersonConverter;
import br.org.kinflasy.apis.people.repositories.InactivePersonRepository;
import br.org.kinflasy.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InactivePersonService {

    private static final String NOT_FOUND_MESSAGE = "Pessoa não encontrada.";

    private final InactivePersonRepository repository;
    private final InactivePersonConverter converter;

    private final AddressClient addressService;

    public List<InactivePersonDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public InactivePersonDto create(final InactivePersonRequest request) {
        // Salvar endereço
        final var address = addressService.create(request.getAddress());

        // Salvar pessoa
        final var entity = converter.toEntity(request);
        entity.setAddressId(address.getId());
        repository.save(entity);

        return converter.toDto(entity);
    }

    public InactivePersonDto findById(final UUID id) {
        final var entity = repository.findById(id);
        return converter.toDto(entity);
    }

    public InactivePersonDto update(final UUID id, final InactivePersonRequest request) {
        // Atualizar original
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var modified = converter.toEntity(request, original);
        repository.save(modified);

        // Atualizar endereço
        addressService.update(original.getAddressId(), request.getAddress());

        return converter.toDto(modified);
    }

    public void delete(final UUID id) {
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        repository.delete(original);
        addressService.delete(original.getAddressId());
    }

}
