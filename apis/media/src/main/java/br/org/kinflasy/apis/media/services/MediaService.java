package br.org.kinflasy.apis.media.services;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.media.entities.Media;
import br.org.kinflasy.apis.media.repositories.MediaRepository;
import br.org.kinflasy.libs.media.contracts.StorageService;
import br.org.kinflasy.libs.media.dto.MediaDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MediaService {

    private final ModelMapper modelMapper;

    private final MediaRepository repository;

    private final StorageService storageService;

    @Transactional
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

        return modelMapper.map(saved, MediaDto.class)
                .setUrl(storageService.getUrl(saved.getId().toString()));
    }

    public Optional<MediaDto> getMetadata(final UUID id) {
        return repository.findById(id)
                .map(entity -> modelMapper.map(entity, MediaDto.class)
                        .setUrl(storageService.getUrl(entity.getId().toString())));
    }

    public Optional<MediaDto.WithContent> download(final UUID id) {
        return repository.findById(id)
                .map(entity -> {
                    try {
                        final var content = storageService.download(id.toString());
                        return modelMapper.map(entity, MediaDto.WithContent.class)
                                .setContent(content);
                    } catch (final IOException e) {
                        return null;
                    }
                });
    }

    @Transactional
    public void delete(final UUID id) throws IOException {
        // Deletar o arquivo do sistema de armazenamento
        storageService.delete(id.toString());

        // Deletar os metadados do banco de dados
        repository.deleteById(id);
    }

}
