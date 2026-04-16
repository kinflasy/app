package br.org.kinflasy.apis.media.services;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.media.entities.Media;
import br.org.kinflasy.apis.media.repositories.MediaRepository;
import br.org.kinflasy.libs.media.dto.MediaDto;
import br.org.kinflasy.libs.media.services.StorageService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MediaService {

    private final ModelMapper modelMapper;

    private final MediaRepository repository;

    private final StorageService storageService;

    public MediaDto upload(final byte[] content, final String fileName, final String mimeType, final long fileSize)
            throws IOException {
        // Construir a entidade de metadados
        final var entity = new Media();
        entity.setOriginalFilename(fileName);
        entity.setMimeType(mimeType);
        entity.setFileSize(fileSize);

        // Salvar os metadados no banco de dados
        final var saved = repository.save(entity);

        // Fazer upload do arquivo no sistema de armazenamento
        storageService.upload(content, saved.getId().toString());

        return modelMapper.map(saved, MediaDto.class);
    }

}
