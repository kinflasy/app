package br.org.kinflasy.api.apis.people.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.people.converters.UserConverter;
import br.org.kinflasy.api.apis.people.repositories.UserRepository;
import br.org.kinflasy.api.libs.people.dto.UserDto;
import br.org.kinflasy.api.libs.people.dto.UserRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserConverter converter;

    public List<UserDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public UserDto create(final UserRequest.Create form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public UserDto findById(final UUID id) {
        final var entity = repository.findById(id);
        return converter.toDto(entity);
    }

    public UserDto update(final UserRequest.Update form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
