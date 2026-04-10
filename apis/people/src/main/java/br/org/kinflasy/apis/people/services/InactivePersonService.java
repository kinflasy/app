package br.org.kinflasy.apis.people.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people.clients.AddressClient;
import br.org.kinflasy.apis.people.converters.InactivePersonConverter;
import br.org.kinflasy.apis.people.repositories.InactivePersonRepository;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import br.org.kinflasy.libs.people.dto.PersonIdentifierDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InactivePersonService {

    private static final String NOT_FOUND_MESSAGE = "Pessoa não encontrada.";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

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

    @Transactional
    @PreAuthorize("@fga.check('church', #request.churchId, 'unit_admin', 'user', principal.id)")
    public InactivePersonDto create(final InactivePersonRequest request) {
        // Salvar pessoa
        final var entity = converter.toEntity(request);
        entity.setId(null);

        // Salvar endereço
        Optional.ofNullable(request.getAddress())
                .map(addressClient::create)
                .ifPresent(address -> entity.setAddressId(address.getId()));

        repository.save(entity);

        // Gerar DTO
        final var dto = converter.toDto(entity);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

    @Transactional
    @PreAuthorize("@fga.check('church', #request.churchId, 'unit_admin', 'user', principal.id) or #request.userId.equals(principal.id)")
    public InactivePersonDto create(final InactivePersonRequest.FromUser request) {
        // Obter dados do usuário a ser desativado
        final var originalUser = userService.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var originalAddress = addressClient.findById(originalUser.getAddressId());

        // Transformar em requisições de nova pessoa inativa
        final var addressRequest = mapper.map(originalAddress, AddressRequest.class);
        final var inactivePersonRequest = mapper.map(originalUser, InactivePersonRequest.class);
        inactivePersonRequest.setChurchId(request.getChurchId())
                .setAddress(addressRequest);

        // Delegar request para o método principal continuar
        return create(inactivePersonRequest);
    }

    @PreAuthorize("@fga.check('person_data', #id, 'can_view', 'user', principal.id)")
    public Optional<InactivePersonDto> findById(final UUID id) {
        return repository.findById(id).map(converter::toDto);
    }

    @Transactional
    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public InactivePersonDto update(final UUID id, final InactivePersonRequest request) {
        // Obter original
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var originalDto = mapper.map(original, InactivePersonDto.class);

        // Atualizar original
        final var modified = converter.toEntity(request, original);
        repository.save(modified);

        // Atualizar endereço
        addressClient.update(original.getAddressId(), request.getAddress());

        // Gerar DTO
        final var dto = converter.toDto(modified);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Updated<>(originalDto, dto));

        return dto;
    }

    @Transactional
    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        // Obter dados
        final var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var dto = mapper.map(entity, InactivePersonDto.class);

        // Deletar
        repository.delete(entity);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Deleted<>(dto));

        // Deletar endereço
        addressClient.delete(entity.getAddressId());
    }

}
