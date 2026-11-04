package br.org.kinflasy.apis.calendar.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import io.swagger.v3.oas.annotations.Operation;

@FeignClient(name = "calendar-membershipsApi", url = "${CHURCHES_API_URL}", path = "church/unit/memberships")
public interface MembershipClient {

    @GetMapping("{id}/integrations")
    @Operation(summary = "Listar integrações", description = "Listar as integrações de uma membresia.")
    public List<IntegrationDto> listIntegrations(@PathVariable final UUID id);

}
