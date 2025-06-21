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

import br.org.kinflasy.api.dto.core.CreateUser;
import br.org.kinflasy.api.dto.core.UserDTO;
import br.org.kinflasy.api.dto.core.UpdateUser;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.services.core.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/core/users")
@Tag(name = "User")
public class UserController {

    private final UserService service;

    public UserController(@Autowired final UserService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todos os usuários ativos cadastrados.")
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(service.dto().findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar", description = "Cadastrar um novo usuário ativo.")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid final CreateUser form) {
        final User savedItem = service.create(form.toUser());
        return new ResponseEntity<>(UserDTO.ofNullable(savedItem), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar um usuário ativo pelo ID.")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") final UUID id) {
        try {
            return new ResponseEntity<>(UserDTO.ofNullable(service.findById(id)), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de um usuário ativo.")
    public ResponseEntity<UserDTO> update(@PathVariable("id") final UUID id,
            @RequestBody @Valid final UpdateUser form) {
        try {
            final var existingItem = service.findById(id);
            return new ResponseEntity<>(UserDTO.ofNullable(service.update(form.update(existingItem))),
                    HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar um usuário ativo, removendo-o do sistema.")
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
