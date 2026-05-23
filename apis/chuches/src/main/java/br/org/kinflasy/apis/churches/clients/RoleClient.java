package br.org.kinflasy.apis.churches.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.people.dto.roles.RoleDto;

@FeignClient(name = "churches-rolesApi", url = "${PEOPLE_API_URL}", path = "roles")
public interface RoleClient {

    @GetMapping
    List<RoleDto> findAll();

    @GetMapping("{id}")
    RoleDto findById(@PathVariable final UUID id);

}
