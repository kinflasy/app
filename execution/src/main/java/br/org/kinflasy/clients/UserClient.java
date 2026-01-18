package br.org.kinflasy.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.people.dto.UserDto;

@FeignClient(name = "usersApi", contextId = "execution-usersApi")
public interface UserClient {

    @GetMapping("@{username}")
    UserDto findByUsername(@PathVariable String username);

}
