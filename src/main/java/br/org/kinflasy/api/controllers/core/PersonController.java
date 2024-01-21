package br.org.kinflasy.api.controllers.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.api.dto.core.CreatePerson;
import br.org.kinflasy.api.dto.core.PersonDTO;
import br.org.kinflasy.api.dto.core.UpdatePerson;
import br.org.kinflasy.api.services.core.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/core/people")
@Tag(name = "Person")
public class PersonController {

    private final PersonService service;

    public PersonController(@Autowired final PersonService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todas as pessoas cadastradas.")
    public ResponseEntity<List<PersonDTO>> getAll() {
        return new ResponseEntity<>(service.dto().findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar", description = "Cadastrar uma nova pessoa.")
    public ResponseEntity<PersonDTO> create(@RequestBody @Valid final @NonNull CreatePerson form) {
        return new ResponseEntity<>(service.dto().create(form.toPerson()), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma pessoa pelo ID.")
    public ResponseEntity<PersonDTO> getById(@PathVariable("id") final @NonNull Integer id) {
        try {
            return new ResponseEntity<>(service.dto().findById(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de uma pessoa.")
    public ResponseEntity<PersonDTO> update(@PathVariable("id") final @NonNull Integer id,
            @RequestBody final @NonNull UpdatePerson form) {
        try {
            final var existingItem = service.findById(id);
            return new ResponseEntity<>(service.dto().update(form.update(existingItem)), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar uma pessoa, removendo-a do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") final @NonNull Integer id) {
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
