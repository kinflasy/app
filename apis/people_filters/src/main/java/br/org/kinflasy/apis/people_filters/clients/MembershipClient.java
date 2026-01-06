package br.org.kinflasy.apis.people_filters.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;

@FeignClient("church-base-api")
@RequestMapping("unit/memberships")
public interface MembershipClient {

    @GetMapping("{id}/integrations")
    public List<IntegrationDto> listIntegrations(@PathVariable final UUID id);

}
