package br.org.kinflasy.apis.churches.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.converters.ChurchConverter;
import br.org.kinflasy.apis.churches.repositories.ChurchRepository;
import br.org.kinflasy.libs.churches.dto.ChurchDto;
import br.org.kinflasy.libs.churches.dto.ChurchRequest;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChurchService {

    private static final String NOT_FOUND_MESSAGE = "Igreja não encontrada";

    private final ChurchRepository repository;
    private final ChurchConverter converter;

    private final UnitService unitService;

    public List<ChurchDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public ChurchDto create(final ChurchRequest request) {
        final var entity = converter.toEntity(request);
        repository.save(entity);
        return converter.toDto(entity);
    }

    @PreAuthorize("@churchSecurityService.isMemberOfChurch(#id, null, principal)")
    public ChurchDto findById(final UUID id) {
        return repository.findById(id).map(converter::toDto)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@churchSecurityService.isMemberOfChurch(#id, null, principal)")
    public ChurchDto update(final UUID id, final ChurchRequest request) {
        return repository.findById(id)
                .map(original -> {
                    final var modified = converter.toEntity(request, original);

                    repository.save(modified);
                    return converter.toDto(modified);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public void delete(final UUID id) {
        // Excluir unidades
        unitService.listByChurchId(id)
                .forEach(unit -> unitService.delete(unit.getId()));

        // Excluir igreja
        repository.deleteById(id);
    }

    public List<UnitDto> listUnits(final UUID id) {
        return repository.findById(id)
                .map(church -> unitService.listByChurchId(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public UnitDto createUnit(final UUID id, final UnitRequest request) {
        return repository.findById(id)
                .map(ignoredChurch -> unitService.create(id, request))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

}
