package br.org.kinflasy.apis.people.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.people.converters.PersonConverter;
import br.org.kinflasy.apis.people.services.PersonService;
import br.org.kinflasy.libs.people.dto.PersonDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/people")
@Tag(name = "Person")
@AllArgsConstructor
public class PersonController {

    private final PersonService service;
    private final PersonConverter converter;

    @GetMapping("admin")
    @Operation(summary = "ADMIN - Listar todos", description = "Listar todas as pessoas cadastradas.")
    public ResponseEntity<List<PersonDto>> listAll() {
        return new ResponseEntity<>(service.findAll().stream().map(converter::toDto).toList(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma pessoa pelo ID.")
    public ResponseEntity<PersonDto> findById(@PathVariable("id") final UUID id) {
        try {
            final var entity = service.findById(id);
            final var dto = converter.toDto(entity);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
