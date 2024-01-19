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
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.services.core.PersonService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("v1/core/users")
public class PersonController {

    private final PersonService service;

    public PersonController(@Autowired final PersonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAll() {
        return new ResponseEntity<>(service.findAll().stream().map(PersonDTO::of).toList(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PersonDTO> create(@NonNull @RequestBody final CreatePerson form) {
        final Person savedItem = service.create(form.toPerson());
        return new ResponseEntity<>(PersonDTO.of(savedItem), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDTO> getById(@PathVariable("id") @NonNull final Integer id) {
        try {
            return new ResponseEntity<>(PersonDTO.of(service.findById(id)), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<PersonDTO> update(@PathVariable("id") @NonNull Integer id, @RequestBody UpdatePerson form) {
        try {
            final var existingItem = service.findById(id);
            return new ResponseEntity<>(PersonDTO.of(service.update(form.transferTo(existingItem))), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") @NonNull Integer id) {
        try {
            service.delete(service.findById(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
