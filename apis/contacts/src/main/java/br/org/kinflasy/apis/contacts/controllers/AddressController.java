package br.org.kinflasy.apis.contacts.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.contacts.services.AddressService;
import br.org.kinflasy.libs.contacts.dto.AddressDto;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/addresses")
@Tag(name = "Address")
@AllArgsConstructor
public class AddressController {

    private final AddressService service;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todos os endereços cadastrados.")
    public ResponseEntity<List<AddressDto>> listAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar", description = "Cadastrar um endereço.")
    public ResponseEntity<AddressDto> create(@RequestBody @Valid final AddressRequest form) {
        return new ResponseEntity<>(service.create(form), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    @Transactional
    @Operation(summary = "Cadastrar durante cadastro de usuário", description = "Cadastrar um endereço enquanto um novo usuário está sendo criado.")
    public ResponseEntity<AddressDto> create(@RequestBody @Valid final AddressRequest form,
            @RequestParam final UUID createdBy) {
        return new ResponseEntity<>(service.create(form, createdBy), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar um endereço pelo ID.")
    public ResponseEntity<AddressDto> findById(@PathVariable final UUID id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de um endereço.")
    public ResponseEntity<AddressDto> update(@PathVariable final UUID id, @RequestBody final AddressRequest form) {
        try {
            return new ResponseEntity<>(service.update(id, form), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar um endereço.")
    public ResponseEntity<HttpStatus> delete(@PathVariable final UUID id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
