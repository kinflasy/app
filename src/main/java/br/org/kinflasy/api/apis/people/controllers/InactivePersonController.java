package br.org.kinflasy.api.apis.people.controllers;

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

import br.org.kinflasy.api.apis.people.services.InactivePersonService;
import br.org.kinflasy.api.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.api.libs.people.dto.InactivePersonRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/inactive-people")
@Tag(name = "Inactive Person")
@AllArgsConstructor
public class InactivePersonController {

    private final InactivePersonService service;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todas as pessoas inativas cadastradas.")
    public ResponseEntity<List<InactivePersonDto>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar", description = "Cadastrar uma nova pessoa inativa.")
    public ResponseEntity<InactivePersonDto> create(@RequestBody @Valid final InactivePersonRequest.Create request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma pessoa inativa pelo ID.")
    public ResponseEntity<InactivePersonDto> getById(@PathVariable final UUID id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de uma pessoa inativa.")
    public ResponseEntity<InactivePersonDto> update(@PathVariable final UUID id,
            @RequestBody final InactivePersonRequest.Update request) {
        try {
            return new ResponseEntity<>(service.update(request), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar uma pessoa inativa, removendo-a do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable final UUID id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
