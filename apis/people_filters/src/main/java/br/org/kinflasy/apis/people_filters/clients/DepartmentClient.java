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
import org.springframework.web.bind.annotation.RequestMapping;

import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionRequest;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationRequest;
import br.org.kinflasy.libs.churches.enums.department.Extension;

@FeignClient("church-base-api")
@RequestMapping("unit/departments")
public interface DepartmentClient {

    @GetMapping("{id}")
    public DepartmentDto findById(@PathVariable final UUID id);

    @PutMapping("{id}")
    public DepartmentDto update(@PathVariable final UUID id, @RequestBody final DepartmentRequest request);

    @DeleteMapping("{id}")
    public HttpStatus delete(@PathVariable final UUID id);

    @GetMapping("{id}/extensions")
    public List<Extension> listExtensions(@PathVariable final UUID id);

    @PostMapping("{id}/extensions")
    public ExtensionSubscriptionDto associateExtension(@PathVariable final UUID id,
            @RequestBody ExtensionSubscriptionRequest request);

    @GetMapping("{id}/extensions/{extension}")
    public ExtensionSubscriptionDto findExtension(@PathVariable final UUID id, @PathVariable final Extension extension);

    @DeleteMapping("{id}/extensions")
    public void dissociateExtension(@PathVariable final UUID id, @RequestBody ExtensionSubscriptionRequest request);

    @GetMapping("{id}/integrants")
    public List<IntegrationDto> listIntegrants(@PathVariable final UUID id);

    @GetMapping("{id}/integration/{membershipId}")
    public IntegrationDto findIntegration(@PathVariable final UUID id, @PathVariable final UUID membershipId);

    @PostMapping("{id}/integrants")
    public IntegrationDto addIntegrant(@PathVariable final UUID id, @RequestBody IntegrationRequest request);

    @DeleteMapping("{id}/integrants")
    public void removeIntegrant(@PathVariable final UUID id, @RequestBody IntegrationRequest request);

}
