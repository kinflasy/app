package br.org.kinflasy.api.controllers.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.api.dto.core.CreatePerson;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.services.core.PersonService;

@RestController
@RequestMapping("v1/users")
public class PersonController {

    private final PersonService service;

    public PersonController(@Autowired final PersonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> create(@NonNull @RequestBody final CreatePerson form) {
        final Person savedItem = service.create(form.toPerson());
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") @NonNull final Integer id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
