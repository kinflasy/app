package br.org.kinflasy.apis.contacts.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.contacts.services.EmailAddressService;
import br.org.kinflasy.libs.contacts.dto.EmailAddressDto;
import br.org.kinflasy.libs.contacts.dto.EmailAddressRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/email-addresses")
@Tag(name = "Email")
@AllArgsConstructor
public class EmailAddressController {

    private final EmailAddressService emailAddressService;

    @PostMapping
    public ResponseEntity<EmailAddressDto> create(@Valid @RequestBody final EmailAddressRequest request) {
        return new ResponseEntity<>(emailAddressService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<EmailAddressDto>> findById(@PathVariable final UUID id) {
        return ResponseEntity.ok(emailAddressService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        emailAddressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
