package br.org.kinflasy.apis.churches.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.people.dto.UserIdentifierDto;
import br.org.kinflasy.libs.people.ids.PersonId;

@FeignClient(name = "churches-usersApi", url = "${PEOPLE_API_URL}", path = "users")
public interface UserClient {

    @GetMapping("identify/{id}")
    UserIdentifierDto identifyById(@PathVariable PersonId id);

    @GetMapping("identify/@{username}")
    UserIdentifierDto identifyByUsername(@PathVariable String username);

}
