package br.org.kinflasy.clients.local;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people.controllers.UserController;
import br.org.kinflasy.clients.UserClient;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserRequest;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserLocalClient implements UserClient {

    private final UserController controller;

    @Override
    public List<UserDto> listAll() {
        return controller.listAll().getBody();
    }

    @Override
    public UserDto create(UserRequest request) {
        return controller.create(request).getBody();
    }

    @Override
    public UserDto findById(UUID id) {
        return controller.findById(id).getBody();
    }

    @Override
    public UserDto findByUsername(String username) {
        return controller.findByUsername(username).getBody();
    }

    @Override
    public UserDto update(UUID id, UserRequest request) {
        return controller.update(id, request).getBody();
    }

    @Override
    public void delete(UUID id) {
        controller.delete(id);
    }

}
