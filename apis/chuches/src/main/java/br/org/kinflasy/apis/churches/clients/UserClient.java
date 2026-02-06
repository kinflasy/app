package br.org.kinflasy.apis.churches.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.people.dto.UserDto;

@FeignClient(name = "usersApi", contextId = "churches-usersApi")
public interface UserClient {

    @GetMapping("{id}")
    UserDto findById(@PathVariable UUID id);

}
