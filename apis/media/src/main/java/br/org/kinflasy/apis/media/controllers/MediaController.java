package br.org.kinflasy.apis.media.controllers;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Operation(summary = "Fazer upload", description = "Fazer upload de um arquivo.")
    public ResponseEntity<MediaDto> upload(@RequestPart final MultipartFile file) throws IOException {
        final var uploaded = service.upload(file.getBytes(), file.getOriginalFilename(), file.getContentType(),
                file.getSize());
        return ResponseEntity.status(HttpStatus.CREATED).body(uploaded);
    }

    @GetMapping("{id}")
    @Operation(summary = "Consultar metadados", description = "Obter metadados de um arquivo.")
    public ResponseEntity<MediaDto> getMetadata(@PathVariable final UUID id) {
        return service.getMetadata(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "{id}/download")
    @Operation(summary = "Fazer download", description = "Fazer download de um arquivo.")
    public ResponseEntity<byte[]> download(@PathVariable final UUID id) {
        return service.download(id)
                .map(mediaWithContent -> {
                    final var headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType(mediaWithContent.getMimeType()));
                    headers.setContentDispositionFormData("attachment", mediaWithContent.getOriginalFilename());
                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(mediaWithContent.getContent());
                })
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}/delete")
    @Operation(summary = "Deletar arquivo", description = "Deletar um arquivo.")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) throws IOException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
