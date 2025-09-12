package br.org.kinflasy.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserRequest;

@Component
public interface UserClient {

    List<UserDto> listAll();

    UserDto create(UserRequest request);

    UserDto findById(UUID id);

    UserDto findByUsername(String username);
    
    UserDto update(UUID id, UserRequest request);

    void delete(UUID id);

}
