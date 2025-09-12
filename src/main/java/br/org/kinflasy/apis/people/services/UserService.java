package br.org.kinflasy.apis.people.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people.converters.UserConverter;
import br.org.kinflasy.apis.people.repositories.UserRepository;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private static final String NOT_FOUND_MESSAGE = "Usuário não encontrado.";

    private final UserRepository repository;
    private final UserConverter converter;

    public List<UserDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public UserDto create(final UserRequest form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public UserDto findById(final UUID id) {
        return repository.findById(id)
                .map(converter::toDto)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public UserDto findByUsername(final String username) {
        return repository.findByUsername(username)
                .map(converter::toDto)
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
