package br.org.kinflasy.apis.contacts.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.contacts.services.LinkService;
import br.org.kinflasy.libs.contacts.dto.LinkDto;
import br.org.kinflasy.libs.contacts.dto.LinkRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/links")
@Tag(name = "Links")
@AllArgsConstructor
public class LinkController {

    private final LinkService service;

    @PostMapping
    @Operation(summary = "Cadastrar", description = "Cadastrar um link.")
    public ResponseEntity<LinkDto> create(final @RequestBody @Valid LinkRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar por ID", description = "Buscar um link pelo ID.")
    public ResponseEntity<LinkDto> findById(final @PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar", description = "Atualizar um link.")
    public ResponseEntity<LinkDto> update(final @PathVariable UUID id, final @RequestBody @Valid LinkRequest request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar", description = "Deletar um link.")
    public ResponseEntity<Void> delete(final @PathVariable UUID id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
