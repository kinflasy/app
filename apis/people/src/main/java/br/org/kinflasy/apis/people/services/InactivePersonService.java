package br.org.kinflasy.apis.people.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people.clients.AddressClient;
import br.org.kinflasy.apis.people.converters.InactivePersonConverter;
import br.org.kinflasy.apis.people.repositories.InactivePersonRepository;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import br.org.kinflasy.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import br.org.kinflasy.libs.people.dto.PersonIdentifierDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InactivePersonService {

    private static final String NOT_FOUND_MESSAGE = "Pessoa não encontrada.";

    private final ModelMapper mapper;

    private final InactivePersonRepository repository;
    private final InactivePersonConverter converter;

    private final UserService userService;

    private final AddressClient addressClient;

    /*
     * ACESSO PÚBLICO
     */

    public Optional<PersonIdentifierDto> identifyById(final UUID id) {
        return repository.findById(id)
                .map(entity -> mapper.map(entity, PersonIdentifierDto.class));
    }

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('unit_admin', #request.churchId, 'admin', 'user', principal.id)")
    public InactivePersonDto create(final InactivePersonRequest request) {
        // Salvar endereço
        final var address = addressClient.create(request.getAddress());

        // Salvar pessoa
        final var entity = converter.toEntity(request);
        entity.setId(null);
        entity.setAddressId(address.getId());
        repository.save(entity);

        return converter.toDto(entity);
    }

    @PreAuthorize("#originalUser.id.equals(principal.id)")
    public InactivePersonDto create(final InactivePersonRequest.FromUser request) {
        final var originalUser = userService.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var originalAddress = addressClient.findById(originalUser.getAddressId());

        // Gerar requisições de nova pessoa inativa
        final var addressRequest = mapper.map(originalAddress, AddressRequest.class);
        final var inactivePersonRequest = mapper.map(originalUser, InactivePersonRequest.class);
        inactivePersonRequest.setChurchId(request.getChurchId())
                .setAddress(addressRequest);

        return create(inactivePersonRequest);
    }

    @PreAuthorize("@fga.check('person_data', #id, 'can_view', 'user', principal.id)")
    public Optional<InactivePersonDto> findById(final UUID id) {
        return repository.findById(id).map(converter::toDto);
    }

    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public InactivePersonDto update(final UUID id, final InactivePersonRequest request) {
        // Atualizar original
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var modified = converter.toEntity(request, original);
        repository.save(modified);

        // Atualizar endereço
        addressClient.update(original.getAddressId(), request.getAddress());

        return converter.toDto(modified);
    }

    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        repository.delete(original);
        addressClient.delete(original.getAddressId());
    }

}
