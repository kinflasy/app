package br.org.kinflasy.apis.people.controllers.roles;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.people.services.roles.RoleService;
import br.org.kinflasy.libs.people.dto.roles.RoleDto;
import br.org.kinflasy.libs.people.dto.roles.RoleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/roles")
@Tag(name = "Roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    @Operation(summary = "Listar todos os papéis", description = "Listar todos os papéis a nível global.")
    public ResponseEntity<List<RoleDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar papel por ID", description = "Buscar um papel específico por seu ID.")
    public ResponseEntity<RoleDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar papel", description = "Cadastrar um novo papel.")
    public ResponseEntity<RoleDto> create(@RequestBody @Valid final RoleRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remover papel", description = "Remover um papel específico por seu ID.")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
