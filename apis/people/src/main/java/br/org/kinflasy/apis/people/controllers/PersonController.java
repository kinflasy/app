package br.org.kinflasy.apis.people.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.kinflasy.apis.people.services.PersonService;
import br.org.kinflasy.libs.people.dto.PersonDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/people")
@Tag(name = "Person")
@AllArgsConstructor
public class PersonController {

    private final PersonService service;

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma pessoa pelo ID.")
    public ResponseEntity<PersonDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping(value = "{id}/profile-image", consumes = "multipart/form-data")
    @Operation(summary = "Atualizar imagem de perfil", description = "Atualizar a imagem de perfil de uma pessoa.")
    public ResponseEntity<PersonDto> updateProfileImage(@PathVariable final UUID id,
            @RequestPart final MultipartFile file) {
        return service.updateProfileImage(id, file)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping(value = "profile-image", consumes = "multipart/form-data")
    @Operation(summary = "Atualizar a própria imagem de perfil", description = "Atualizar a imagem de perfil do usuário logado.")
    public ResponseEntity<PersonDto> updateProfileImage(@RequestPart final MultipartFile file) {
        return service.updateProfileImage(file)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}/profile-image")
    @Operation(summary = "Deletar imagem de perfil", description = "Deletar a imagem de perfil de uma pessoa.")
    public ResponseEntity<PersonDto> deleteProfileImage(@PathVariable final UUID id) {
        return service.deleteProfileImage(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("profile-image")
    @Operation(summary = "Deletar a própria imagem de perfil", description = "Deletar a imagem de perfil do usuário logado.")
    public ResponseEntity<PersonDto> deleteProfileImage() {
        return service.deleteProfileImage()
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

}
