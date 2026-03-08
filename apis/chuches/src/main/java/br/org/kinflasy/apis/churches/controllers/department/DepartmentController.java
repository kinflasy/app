package br.org.kinflasy.apis.churches.controllers.department;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.apis.churches.services.department.IntegrationService;
import br.org.kinflasy.libs.churches.contracts.access_rules.AccessRule;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionRequest;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto.Pending;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationRequest;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/church/unit/departments")
@Tag(name = "Department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService service;
    private final IntegrationService integrationService;

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar um departamento pelo ID.")
    public ResponseEntity<DepartmentDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de um departamento.")
    public ResponseEntity<DepartmentDto> update(@PathVariable final UUID id,
            @RequestBody final DepartmentRequest request) {
        try {
            return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar um departamento, removendo-o do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable final UUID id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("{id}/extensions")
    public ResponseEntity<List<Extension>> listExtensions(@PathVariable final UUID id) {
        try {
            return new ResponseEntity<>(service.listExtensions(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{id}/extensions")
    public ResponseEntity<ExtensionSubscriptionDto> subscribeToExtension(@PathVariable final UUID id,
            @RequestBody final ExtensionSubscriptionRequest request) {
        try {
            return new ResponseEntity<>(service.subscribeToExtension(id, request), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}/extensions/{extension}")
    public ResponseEntity<ExtensionSubscriptionDto> findExtension(@PathVariable final UUID id,
            @PathVariable final Extension extension) {
        return service.findExtension(id, extension)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}/extensions")
    public ResponseEntity<Void> dissociateExtension(@PathVariable final UUID id,
            @RequestBody final ExtensionSubscriptionRequest request) {
        try {
            service.dissociateExtension(id, request);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}/integrants")
    public ResponseEntity<List<IntegrationDto>> listIntegrants(@PathVariable final UUID id) {
        return ResponseEntity.ok(integrationService.listByDepartment(id));
    }

    @GetMapping("{id}/integration/{membershipId}")
    public ResponseEntity<IntegrationDto> findIntegration(@PathVariable final UUID id,
            @PathVariable final UUID membershipId) {
        return integrationService.findByDepartmentAndMembership(id, membershipId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping("{id}/integrants")
    public ResponseEntity<IntegrationDto> addIntegrant(@PathVariable final UUID id,
            @RequestBody final IntegrationRequest request) {
        return ResponseEntity.ok(integrationService.create(id, request));
    }

    @DeleteMapping("{id}/integrants")
    public ResponseEntity<Void> removeIntegrant(@PathVariable final UUID id,
            @RequestBody final IntegrationRequest request) {
        integrationService.deleteByDepartmentAndMembership(id, request.getMembershipId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/join")
    public ResponseEntity<Pending> askToJoin(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.askToJoin(id));
    }

    @GetMapping("{id}/pending")
    public ResponseEntity<List<Pending>> listPendingByDepartment(@PathVariable final UUID id) {
        return ResponseEntity.ok(integrationService.listPendingByDepartment(id));
    }

    @PostMapping("{id}/pending")
    public ResponseEntity<IntegrationDto> confirmPending(@PathVariable final UUID id,
            @RequestBody final IntegrationRequest request) {
        return ResponseEntity.ok(integrationService.confirmPending(id, request.getMembershipId(), request.getType()));
    }

    @DeleteMapping("{id}/pending/{membershipId}")
    public ResponseEntity<Void> deletePending(@PathVariable final UUID id, @PathVariable final UUID membershipId) {
        integrationService.deletePending(id, membershipId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/visibility-rules")
    public ResponseEntity<List<AccessRule>> listVisibilityRules(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.listVisibilityRules(id));
    }

    @GetMapping("{id}/join-rules")
    public ResponseEntity<List<AccessRule>> listJoinRules(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.listJoinRules(id));
    }

}
