package br.org.kinflasy.api.apis.churches.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.churches.converters.ChurchConverter;
import br.org.kinflasy.api.apis.churches.converters.UnitConverter;
import br.org.kinflasy.api.apis.churches.repositories.ChurchRepository;
import br.org.kinflasy.api.libs.churches.dto.ChurchDto;
import br.org.kinflasy.api.libs.churches.dto.ChurchRequest;
import br.org.kinflasy.api.libs.churches.dto.UnitDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChurchService {

    private final ChurchRepository repository;
    private final ChurchConverter converter;
    private final UnitConverter unitConverter;

    public List<ChurchDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public ChurchDto create(final ChurchRequest.Create form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public ChurchDto findById(final UUID id) {
        return repository.findById(id).map(converter::toDto).orElseThrow();
    }

    public ChurchDto update(final ChurchRequest.Update form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public void delete(final UUID id) {
        repository.deleteById(id);
    }

    public List<UnitDto> getUnits(final UUID id) {
        return repository.findById(id).orElseThrow()
                .getUnits().stream()
                .map(unitConverter::toDto)
                .toList();
    }

}
