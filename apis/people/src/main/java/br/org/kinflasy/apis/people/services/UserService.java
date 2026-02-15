package br.org.kinflasy.apis.people.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people.clients.AddressClient;
import br.org.kinflasy.apis.people.clients.ChurchClient;
import br.org.kinflasy.apis.people.converters.UserConverter;
import br.org.kinflasy.apis.people.repositories.UserRepository;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.libs.people.dto.DeactivationRequest;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserIdentifierDto;
import br.org.kinflasy.libs.people.dto.UserRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private static final String NOT_FOUND_MESSAGE = "Usuário não encontrado.";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final UserRepository repository;
    private final UserConverter converter;

    private final AddressClient addressClient;
    private final ChurchClient churchClient;

    /*
     * ACESSO PÚBLICO
     */

    public UserDto create(final UserRequest request) {
        // Salvar usuário
        final var entity = converter.toEntity(request);
        final var savedUser = repository.save(entity);

        // Salvar endereço
        final var address = addressClient.create(request.getAddress(), savedUser.getId());

        // Referenciar endereço
        savedUser.setAddressId(address.getId());

        // Gerar DTO
        final var dto = converter.toDto(entity);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

    public Optional<UserIdentifierDto> identifyById(final UUID id) {
        return repository.findById(id)
                .map(entity -> mapper.map(entity, UserIdentifierDto.class));
    }

    public UserIdentifierDto identifyByUsername(final String username) {
        return repository.findByUsername(username)
                .map(entity -> mapper.map(entity, UserIdentifierDto.class))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('person_data', #id, 'can_view', 'user', principal.id)")
    public Optional<UserDto> findById(final UUID id) {
        return repository.findById(id)
                .map(converter::toDto);
    }

    @PostAuthorize("@fga.check('person_data', returnObject.id, 'can_view', 'user', principal.id)")
    public UserDto findByUsername(final String username) {
        return repository.findByUsername(username)
                .map(user -> mapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public UserDto update(final UUID id, final UserRequest form) {
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var modified = converter.toEntity(form, original);

        // Salvar
        repository.save(modified);

        // Gerar DTOs
        final var originalDto = converter.toDto(original);
        final var modifiedDto = converter.toDto(modified);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Updated<>(originalDto, modifiedDto));

        return modifiedDto;
    }

    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        repository.findById(id)
                .ifPresent(entity -> {
                    // Desativar membresias do usuário
                    churchClient.deactivateMember(new DeactivationRequest(id));

                    // Deletar
                    repository.deleteById(id);

                    // Gerar DTO
                    final var dto = mapper.map(entity, UserDto.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(dto));
                });

    }

}
