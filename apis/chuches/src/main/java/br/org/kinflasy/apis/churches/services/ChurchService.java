package br.org.kinflasy.apis.churches.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.converters.ChurchConverter;
import br.org.kinflasy.apis.churches.repositories.ChurchRepository;
import br.org.kinflasy.libs.churches.dto.ChurchDto;
import br.org.kinflasy.libs.churches.dto.ChurchRequest;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class ChurchService {

    private static final String NOT_FOUND_MESSAGE = "Igreja não encontrada";

    private final ModelMapper mapper;

    private final ChurchRepository repository;
    private final ChurchConverter converter;

    private final UnitService unitService;

    /*
     * ACESSO PÚBLICO
     */

    public List<ChurchDto> findAll() {
        log.info("Listando todas as Igrejas...");
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public Optional<ChurchDto> findById(final UUID id) {
        log.info("Buscando Igreja de id {}...", id);
        return repository.findById(id).map(converter::toDto);
    }

    public List<UnitDto> listUnits(final UUID id) {
        log.info("Listando todas as unidades da Igreja de id {}...", id);
        return repository.findById(id)
                .map(church -> unitService.listByChurchId(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public List<ChurchDto> search(final String term) {
        return repository.findAll(ChurchRepository.searchByTerm(term)).stream()
                .map(entity -> mapper.map(entity, ChurchDto.class))
                .toList();
    }

    /*
     * ACESSO LOGADO
     */

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ChurchDto create(final ChurchRequest request) {
        log.info("Criando Igreja @{}...", request.getSlug());

        final var entity = converter.toEntity(request);
        repository.save(entity);
        log.info("Igreja @{} criada", request.getSlug());

        return converter.toDto(entity);
    }

    /*
     * ACESSO RESTRITO
     */

    @Transactional
    @PreAuthorize("@fga.check('church', #id, 'admin', 'user', principal.id)")
    public ChurchDto update(final UUID id, final ChurchRequest request) {
        log.info("Atualizando Igreja @{} (id {})...", request.getSlug(), id);
        return repository.findById(id)
                .map(original -> {
                    final var modified = converter.toEntity(request, original);

                    repository.save(modified);
                    return converter.toDto(modified);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    @PreAuthorize("@fga.check('church', #id, 'admin', 'user', principal.id)")
    public void delete(final UUID id) {
        log.info("Deletando Igreja de id {}...", id);

        // Excluir unidades
        unitService.listByChurchId(id)
                .forEach(unit -> unitService.delete(unit.getId()));

        // Excluir igreja
        repository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("@fga.check('church', #id, 'admin', 'user', principal.id)")
    public UnitDto createUnit(final UUID id, final UnitRequest request) {
        return repository.findById(id)
                .map(ignoredChurch -> unitService.create(id, request))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

}
