package br.org.kinflasy.apis.churches.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserRequest;

@FeignClient(value = "users-api", contextId = "churches.users-api")
public interface UserClient {

    @PostMapping("admin")
    UserDto create(UserRequest request);

    @GetMapping("{id}")
    UserDto findById(UUID id);

}
