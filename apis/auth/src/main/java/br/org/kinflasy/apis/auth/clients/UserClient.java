package br.org.kinflasy.apis.auth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserRequest;

@FeignClient(name = "usersApi", contextId = "auth-usersApi")
public interface UserClient {

    @PostMapping("admin")
    UserDto create(@RequestBody UserRequest request);

}
