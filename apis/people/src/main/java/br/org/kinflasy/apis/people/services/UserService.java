package br.org.kinflasy.apis.people.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people.clients.AddressClient;
import br.org.kinflasy.apis.people.converters.UserConverter;
import br.org.kinflasy.apis.people.repositories.UserRepository;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserRequest;
import br.org.kinflasy.libs.people.dto.UserWithPasswordDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private static final String NOT_FOUND_MESSAGE = "Usuário não encontrado.";

    private final ModelMapper mapper;

    private final UserRepository repository;
    private final UserConverter converter;

    private final AddressClient addressClient;

    public List<UserDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public UserDto create(final UserRequest request) {
        // Salvar usuário
        final var entity = converter.toEntity(request);
        final var savedUser = repository.save(entity);

        // Salvar endereço
        final var address = addressClient.create(request.getAddress(), savedUser.getId());

        // Referenciar endereço
        savedUser.setAddressId(address.getId());

        return converter.toDto(entity);
    }

    public Optional<UserDto> findById(final UUID id) {
        return repository.findById(id)
                .map(converter::toDto);
    }

    public UserDto findByUsername(final String username) {
        return repository.findByUsername(username)
                .map(user -> mapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public UserWithPasswordDto findByUsernameWithPassword(final String username) {
        return repository.findByUsername(username)
                .map(user -> mapper.map(user, UserWithPasswordDto.class))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public UserDto update(final UUID id, final UserRequest form) {
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var modified = converter.toEntity(form, original);

        repository.save(modified);
        return converter.toDto(modified);
    }

    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
