package br.org.kinflasy.apis.churches.services.department;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.converters.department.DepartmentConverter;
import br.org.kinflasy.apis.churches.repositories.department.DepartmentRepository;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentService {

    private static final String NOT_FOUND_MESSAGE = "Departamento não encontrado";

    private final DepartmentRepository repository;
    private final DepartmentConverter converter;

    public List<DepartmentDto> listByUnitId(final UUID unitId) {
        return repository.findByUnitId(unitId).stream()
                .map(converter::toDto)
                .toList();
    }

    public DepartmentDto create(final UUID unitId, final DepartmentRequest request) {
        // Construir departamento
        final var department = converter.toEntity(request);

        // Associar unidade
        department.setUnitId(unitId);

        // Salvar
        final var created = repository.save(department);

        return converter.toDto(created);
    }

    public DepartmentDto findById(final UUID id) {
        final var entity = repository.findById(id);
        return converter.toDto(entity);
    }

    public DepartmentDto update(final UUID id, final DepartmentRequest request) {
        final var original = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        final var modified = converter.toEntity(request, original);

        repository.save(modified);
        return converter.toDto(modified);
    }

    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
