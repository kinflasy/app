package br.org.kinflasy.api.apis.churches.services.department;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.churches.converters.department.DepartmentConverter;
import br.org.kinflasy.api.apis.churches.repositories.department.DepartmentRepository;
import br.org.kinflasy.api.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.api.libs.churches.dto.departments.DepartmentRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentConverter converter;

    
    public List<DepartmentDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public DepartmentDto create(final DepartmentRequest.Create form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public DepartmentDto findById(final UUID id) {
        final var entity = repository.findById(id);
        return converter.toDto(entity);
    }

    public DepartmentDto update(final DepartmentRequest.Update form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
