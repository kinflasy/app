package br.org.kinflasy.apis.people_filters.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionRequest;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationRequest;
import br.org.kinflasy.libs.churches.enums.department.Extension;

@FeignClient(name = "churchBaseApi", contextId = "peopleFilters-churchBaseApi-departments", path = "unit/departments")
public interface DepartmentClient {

    @GetMapping("{id}")
    DepartmentDto findById(@PathVariable final UUID id);

    @PutMapping("{id}")
    DepartmentDto update(@PathVariable final UUID id, @RequestBody final DepartmentRequest request);

    @DeleteMapping("{id}")
    HttpStatus delete(@PathVariable final UUID id);

    @GetMapping("{id}/extensions")
    List<Extension> listExtensions(@PathVariable final UUID id);

    @PostMapping("{id}/extensions")
    ExtensionSubscriptionDto associateExtension(@PathVariable final UUID id,
            @RequestBody ExtensionSubscriptionRequest request);

    @GetMapping("{id}/extensions/{extension}")
    ExtensionSubscriptionDto findExtension(@PathVariable final UUID id, @PathVariable final Extension extension);

    @DeleteMapping("{id}/extensions")
    void dissociateExtension(@PathVariable final UUID id, @RequestBody ExtensionSubscriptionRequest request);

    @GetMapping("{id}/integrants")
    List<IntegrationDto> listIntegrants(@PathVariable final UUID id);

    @GetMapping("{id}/integration/{membershipId}")
    IntegrationDto findIntegration(@PathVariable final UUID id, @PathVariable final UUID membershipId);

    @PostMapping("{id}/integrants")
    IntegrationDto addIntegrant(@PathVariable final UUID id, @RequestBody IntegrationRequest request);

    @DeleteMapping("{id}/integrants")
    void removeIntegrant(@PathVariable final UUID id, @RequestBody IntegrationRequest request);

}
