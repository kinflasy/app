package br.org.kinflasy.api.apis.churches.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.churches.converters.UnitConverter;
import br.org.kinflasy.api.apis.churches.converters.department.DepartmentConverter;
import br.org.kinflasy.api.apis.churches.repositories.UnitRepository;
import br.org.kinflasy.api.libs.churches.dto.UnitDto;
import br.org.kinflasy.api.libs.churches.dto.UnitRequest;
import br.org.kinflasy.api.libs.churches.dto.departments.DepartmentDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnitService {

    private final UnitRepository repository;
    private final UnitConverter converter;
    private final DepartmentConverter departmentConverter;

    public List<UnitDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public UnitDto create(final UnitRequest.Create form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public UnitDto findById(final UUID id) {
        final var entity = repository.findById(id);
        return converter.toDto(entity);
    }

    public UnitDto update(final UnitRequest.Update form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public void delete(final UUID id) {
        repository.deleteById(id);
    }

    public List<DepartmentDto> getDepartments(final UUID id) {
        return repository.findById(id).orElseThrow()
                .getDepartments().stream()
                .map(departmentConverter::toDto)
                .toList();
    }

}
