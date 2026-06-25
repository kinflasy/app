package br.org.kinflasy.apis.people.services;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people.adapters.UserIdentifierDtoAdapter;
import br.org.kinflasy.apis.people.clients.AddressClient;
import br.org.kinflasy.apis.people.clients.ChurchClient;
import br.org.kinflasy.apis.people.converters.UserConverter;
import br.org.kinflasy.apis.people.repositories.UserRepository;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.churches.dto.DeactivationRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.libs.lib_utils.EntityEvent.Created;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserIdentifierDto;
import br.org.kinflasy.libs.people.dto.UserRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private static final String NOT_FOUND_MESSAGE = "Usuário não encontrado.";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;
    private final AuthUtils authUtils;
    private final PasswordEncoder encoder;

    private final UserRepository repository;
    private final UserConverter converter;

    private final AddressClient addressClient;
    private final ChurchClient churchClient;

    /*
     * ACESSO PÚBLICO
     */

    @Transactional
    public UserDto create(final UserRequest request) {
        // Salvar usuário
        final var entity = converter.toEntity(request);

        if (StringUtils.isBlank(entity.getNickname())) {
            final var firstName = entity.getFullName().split(" ")[0];
            entity.setNickname(firstName);
        }

        final var savedUser = repository.save(entity);

        Optional.ofNullable(request.getAddress())
                .ifPresentOrElse(
                        // Salvar endereço
                        addressRequest -> {
                            final var address = addressClient.create(request.getAddress(), savedUser.getId());

                            // Referenciar endereço
                            savedUser.setAddressId(address.getId());
                        },

                        // Ou pular etapa
                        () -> log.info("Endereço não recebido. Pulando etapa..."));

        // Gerar DTO
        final UserDto dto = converter.toDto(entity);

        // Publicar evento
        Created<UserDto> event = new EntityEvent.Created<>(dto);
        publisher.publishEvent(event);

        return dto;
    }

    public Optional<UserIdentifierDto> identifyById(final UUID id) {
        return repository.findById(id)
                .map(UserIdentifierDtoAdapter::new);
    }

    public Optional<UserIdentifierDto> identifyByUsername(final String username) {
        return repository.findByUsername(username)
                .map(UserIdentifierDtoAdapter::new);
    }

    /*
     * ACESSO AUTENTICADO
     */

    @PreAuthorize("isAuthenticated()")
    public Optional<UserIdentifierDto> identifyLoggedUser() {
        final var loggedUser = authUtils.getLoggedUser();
        return identifyById(loggedUser.getId());
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

    @Transactional
    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public UserDto update(final UUID id, final UserRequest request) {
        return repository.findById(id)
                .map(entity -> {
                    final var originalDto = mapper.map(entity, UserDto.class);

                    // Dados obrigatórios: obter da requisição ou manter original
                    final var email = Optional.ofNullable(request.getEmail()).orElseGet(entity::getEmail);
                    final var username = Optional.ofNullable(request.getUsername()).orElseGet(entity::getUsername);
                    final var gender = Optional.ofNullable(request.getGender()).orElseGet(entity::getGender);
                    final var fullName = Optional.ofNullable(request.getFullName()).orElseGet(entity::getFullName);
                    final var birthDate = Optional.ofNullable(request.getBirthDate()).orElseGet(entity::getBirthDate);
                    final var password = Optional.ofNullable(request.getPassword())
                            // Criptografar nova senha recebida
                            .map(encoder::encode)
                            // Ou manter a senha antiga caso não seja recebida uma nova
                            .orElseGet(entity::getPassword);

                    // Atualizar endereço
                    final var addressId = Optional.ofNullable(entity.getAddressId())

                            .map(currentAddressId -> Optional.ofNullable(request.getAddress())
                                    // Caso 1: o endereço antigo e o novo existem -> atualizar
                                    .map(addressRequest -> addressClient.update(currentAddressId, addressRequest)
                                            .getId())

                                    // Caso 2: o endereço antigo existe, mas o novo não -> deletar
                                    .orElseGet(() -> {
                                        addressClient.delete(currentAddressId);
                                        return null;
                                    }))

                            .orElseGet(() -> Optional.ofNullable(request.getAddress())
                                    // Caso 3: o endereço antigo não existe, mas o novo existe -> criar
                                    .map(addressRequest -> addressClient.create(addressRequest).getId())

                                    // Caso 4: o endereço antigo e o novo não existem -> manter como null
                                    .orElse(null));

                    // Atualizar
                    mapper.map(request, entity);
                    entity.setEmail(email);
                    entity.setUsername(username);
                    entity.setPassword(password);
                    entity.setGender(gender);
                    entity.setFullName(fullName);
                    entity.setBirthDate(birthDate);
                    entity.setAddressId(addressId);

                    // Salvar
                    repository.save(entity);
                    final var modifiedDto = mapper.map(entity, UserDto.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Updated<>(originalDto, modifiedDto));

                    return modifiedDto;
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        repository.findById(id)
                .ifPresent(entity -> {
                    // Desativar membresias do usuário
                    churchClient.deactivateMember(new DeactivationRequest().setUserId(id));

                    // Deletar
                    repository.deleteById(id);

                    // Gerar DTO
                    final var dto = mapper.map(entity, UserDto.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(dto));
                });

    }

}
