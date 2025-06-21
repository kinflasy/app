package br.org.kinflasy.api.controllers.core;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.org.kinflasy.api.dto.core.CreateInactivePerson;
import br.org.kinflasy.api.dto.core.InactivePersonDTO;
import br.org.kinflasy.api.dto.core.UpdateInactivePerson;
import br.org.kinflasy.api.services.core.InactivePersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/core/inactive-people")
@Tag(name = "Inactive Person")
public class InactivePersonController {

    private final InactivePersonService service;

    public InactivePersonController(@Autowired final InactivePersonService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todas as pessoas inativas cadastradas.")
    public ResponseEntity<List<InactivePersonDTO>> getAll() {
        return new ResponseEntity<>(service.dto().findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar", description = "Cadastrar uma nova pessoa inativa.")
    public ResponseEntity<InactivePersonDTO> create(@RequestBody @Valid final CreateInactivePerson form) {
        return new ResponseEntity<>(service.dto().create(form.toInactivePerson()), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma pessoa inativa pelo ID.")
    public ResponseEntity<InactivePersonDTO> getById(@PathVariable("id") final UUID id) {
        try {
            return new ResponseEntity<>(service.dto().findById(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de uma pessoa inativa.")
    public ResponseEntity<InactivePersonDTO> update(@PathVariable("id") final UUID id,
            @RequestBody final UpdateInactivePerson form) {
        try {
            final var existingItem = service.findById(id);
            return new ResponseEntity<>(service.dto().update(form.update(existingItem)), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar uma pessoa inativa, removendo-a do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") final UUID id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
