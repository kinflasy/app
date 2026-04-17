package br.org.kinflasy.apis.media.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.kinflasy.apis.media.services.MediaService;
import br.org.kinflasy.libs.media.dto.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/media")
@Tag(name = "Media")
@AllArgsConstructor
public class MediaController {

    private final MediaService service;

    @PostMapping(value = "upload", consumes = "multipart/form-data")
    @Operation(summary = "Upload", description = "Fazer upload de um arquivo.")
    public ResponseEntity<MediaDto> upload(@RequestPart MultipartFile file) throws IOException {
        final var uploaded = service.upload(file.getBytes(), file.getOriginalFilename(), file.getContentType(),
                file.getSize());
        return ResponseEntity.status(HttpStatus.CREATED).body(uploaded);
    }

}
