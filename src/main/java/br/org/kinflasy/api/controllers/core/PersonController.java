package br.org.kinflasy.api.controllers.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.api.dto.core.PersonDTO;
import br.org.kinflasy.api.services.core.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

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
        return new ResponseEntity<>(service.findAll().stream().map(PersonDTO::ofNonNull).toList(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma pessoa pelo ID.")
    public ResponseEntity<PersonDTO> getById(@PathVariable("id") final Integer id) {
        try {
            return new ResponseEntity<>(PersonDTO.ofNonNull(service.findById(id)), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
