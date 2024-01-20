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

import br.org.kinflasy.api.dto.core.CreateUser;
import br.org.kinflasy.api.dto.core.UserDTO;
import br.org.kinflasy.api.dto.core.UpdateUser;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.services.core.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/core/users")
public class UserController {

    private final UserService service;

    public UserController(@Autowired final UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(service.findAll().stream().map(UserDTO::ofNullable).toList(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDTO> create(@RequestBody @Valid final @NonNull CreateUser form) {
        final User savedItem = service.create(form.toUser());
        return new ResponseEntity<>(UserDTO.ofNullable(savedItem), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") final @NonNull Integer id) {
        try {
            return new ResponseEntity<>(UserDTO.ofNullable(service.findById(id)), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<UserDTO> update(@PathVariable("id") final @NonNull Integer id,
            @RequestBody @Valid final @NonNull UpdateUser form) {
        try {
            final var existingItem = service.findById(id);
            return new ResponseEntity<>(UserDTO.ofNullable(service.update(form.transferTo(existingItem))),
                    HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") final @NonNull Integer id) {
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
