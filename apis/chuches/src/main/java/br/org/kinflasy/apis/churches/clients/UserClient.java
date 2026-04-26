package br.org.kinflasy.apis.churches.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.people.dto.UserDto;

@FeignClient(name = "churches-usersApi", url = "${PEOPLE_API_URL}", path = "users")
public interface UserClient {

    @GetMapping("identify/{id}")
    UserDto identifyById(@PathVariable UUID id);

    @GetMapping("identify/@{username}")
    UserDto identifyByUsername(@PathVariable String username);

}
