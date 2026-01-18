package br.org.kinflasy.apis.auth.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.auth.repositories.UserRepository;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserWithPasswordDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service("authUserService")
@AllArgsConstructor
public class UserService {

    private static final String NOT_FOUND_MESSAGE = "Usuário não encontrado.";

    private final ModelMapper mapper;

    private final UserRepository repository;

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

}
