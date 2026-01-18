package br.org.kinflasy.apis.people_filters.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;

@FeignClient(name = "churchBaseApi", contextId = "peopleFilters-churchBaseApi-memberships", path = "unit/memberships")
public interface MembershipClient {

    @GetMapping("{id}/integrations")
    List<IntegrationDto> listIntegrations(@PathVariable final UUID id);

}
