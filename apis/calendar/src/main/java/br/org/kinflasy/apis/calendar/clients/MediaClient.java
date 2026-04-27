package br.org.kinflasy.apis.calendar.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import br.org.kinflasy.libs.media.dto.MediaDto;

@FeignClient(name = "calendar-mediaApi", url = "${MEDIA_API_URL}")
public interface MediaClient {

    @PostMapping(value = "upload", consumes = "multipart/form-data")
    public ResponseEntity<MediaDto> upload(@RequestPart final MultipartFile file);

    @GetMapping("{id}")
    public ResponseEntity<MediaDto> getMetadata(@PathVariable final UUID id);

    @GetMapping(value = "{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable final UUID id);

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable final UUID id);

}
