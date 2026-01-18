package br.org.kinflasy.apis.people.controllers;

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

import br.org.kinflasy.apis.people.services.UserService;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserRequest;
import br.org.kinflasy.libs.people.dto.UserWithPasswordDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/users")
@Tag(name = "User")
@AllArgsConstructor
public class UserController {

    private final UserService service;
    private final AuthUtils authUtils;

    @GetMapping("admin")
    @Operation(summary = "ADMIN - Listar todos", description = "Listar todos os usuários ativos cadastrados.")
    public ResponseEntity<List<UserDto>> listAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping("admin")
    @Transactional
    @Operation(summary = "ADMIN - Cadastrar", description = "Cadastrar um novo usuário ativo.")
    public ResponseEntity<UserDto> create(@RequestBody @Valid final UserRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar um usuário ativo pelo ID.")
    public ResponseEntity<UserDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("@{username}")
    @Operation(summary = "Buscar por username", description = "Buscar um usuário ativo pelo username.")
    public ResponseEntity<UserDto> findByUsername(@PathVariable final String username) {
        try {
            return new ResponseEntity<>(service.findByUsername(username), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("@{username}/with-password")
    @Operation(summary = "Buscar por username, trazendo senha", description = "Buscar um usuário ativo pelo username e traz a senha em hash.")
    public ResponseEntity<UserWithPasswordDto> findByUsernameWithPassword(@PathVariable final String username) {
        try {
            return new ResponseEntity<>(service.findByUsernameWithPassword(username), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados do usuário logado.")
    public ResponseEntity<UserDto> update(@RequestBody @Valid final UserRequest request) {
        try {
            final var loggedUser = authUtils.getLoggedUser();
            return new ResponseEntity<>(service.update(loggedUser.getId(), request), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("admin/{id}")
    @Transactional
    @Operation(summary = "ADMIN - Editar", description = "Editar os dados de um usuário ativo.")
    public ResponseEntity<UserDto> update(@PathVariable final UUID id, @RequestBody @Valid final UserRequest request) {
        try {
            return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    @Transactional
    @Operation(summary = "ADMIN - Excluir", description = "Descadastrar usuário logado, removendo-o do sistema.")
    public ResponseEntity<HttpStatus> delete() {
        try {
            final var loggedUser = authUtils.getLoggedUser();
            service.delete(loggedUser.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("admin/{id}")
    @Transactional
    @Operation(summary = "ADMIN - Excluir", description = "Descadastrar um usuário ativo, removendo-o do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable final UUID id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
