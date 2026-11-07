package br.org.kinflasy.apis.people.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.people.services.UserService;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserIdentifierDto;
import br.org.kinflasy.libs.people.dto.UserRequest;
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

    @PostMapping("admin")
    @Operation(summary = "ADMIN - Cadastrar", description = "Cadastrar um novo usuário ativo.")
    public ResponseEntity<UserDto> create(@RequestBody @Valid final UserRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @GetMapping("identify/{id}")
    @Operation(summary = "Identificar", description = "Identificar um usuário ativo pelo ID.")
    public ResponseEntity<UserIdentifierDto> identifyById(@PathVariable final UUID id) {
        return service.identifyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("identify/@{username}")
    @Operation(summary = "Identificar por username", description = "Identificar um usuário ativo pelo username.")
    public ResponseEntity<UserIdentifierDto> identifyByUsername(@PathVariable final String username) {
        return service.identifyByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("identify")
    @Operation(summary = "Identificar usuário logado", description = "Identificar o usuário logado.")
    public ResponseEntity<UserIdentifierDto> identifyLoggedUser() {
        return service.identifyLoggedUser()
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    @PutMapping
    @Operation(summary = "Editar-se", description = "Editar os dados do usuário logado.")
    public ResponseEntity<UserDto> update(@RequestBody final UserRequest.Update request) {
        return service.update(request)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping("admin/{id}")
    @Operation(summary = "ADMIN - Editar", description = "Editar os dados de um usuário ativo.")
    public ResponseEntity<UserDto> update(@PathVariable final UUID id, @RequestBody final UserRequest.Update request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping("username")
    @Operation(summary = "Mudar username", description = "Mudar o username do usuário logado.")
    public ResponseEntity<Void> updateUsername(@Valid @RequestBody final UserRequest.UpdateUsername request) {
        return service.updateUsername(request)
                .map(ignored -> ResponseEntity.ok().<Void>build())
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping("password")
    @Operation(summary = "Mudar senha", description = "Mudar a senha do usuário logado.")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody final UserRequest.UpdatePassword request) {
        return service.updatePassword(request)
                .map(ignored -> ResponseEntity.ok().<Void>build())
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping
    @Operation(summary = "Excluir-se", description = "Descadastrar usuário logado, removendo-o do sistema.")
    public ResponseEntity<HttpStatus> delete() {
        try {
            service.delete();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("admin/{id}")
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
