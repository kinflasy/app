package br.org.kinflasy.apis.churches.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.converters.UnitConverter;
import br.org.kinflasy.apis.churches.converters.department.DepartmentConverter;
import br.org.kinflasy.apis.churches.repositories.UnitRepository;
import br.org.kinflasy.apis.churches.repositories.department.DepartmentRepository;
import br.org.kinflasy.clients.AddressClient;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnitService {

    private static final String NOT_FOUND_MESSAGE = "Unidade não encontrada";

    private final UnitRepository repository;
    private final UnitConverter converter;

    private final AddressClient addressClient;
    private final DepartmentConverter departmentConverter;
    private final DepartmentRepository departmentRepository;

    public UnitDto findById(final UUID id) {
        return repository.findById(id).map(converter::toDto)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public UnitDto update(final UUID id, final UnitRequest request) {
        return repository.findById(id)
                .map(original -> {
                    final var modified = converter.toEntity(request, original);

                    final var address = addressClient.update(original.getAddressId(), request.getAddress());
                    modified.setAddressId(address.getId());

                    repository.save(modified);
                    return converter.toDto(modified);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public void delete(final UUID id) {
        repository.findById(id)
                .ifPresent(unit -> {
                    addressClient.delete(unit.getAddressId());
                    repository.delete(unit);
                });
    }

    public List<DepartmentDto> listDepartments(final UUID id) {
        return repository.findById(id)
                .map(unit -> unit.getDepartments().stream()
                        .map(departmentConverter::toDto)
                        .toList())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public DepartmentDto createDepartment(final UUID id, final DepartmentRequest request) {
        return repository.findById(id)
                .map(unit -> {
                    // Construir departamento
                    final var department = departmentConverter.toEntity(request);

                    // Associar unidade
                    department.setUnit(unit);

                    // Salvar
                    final var created = departmentRepository.save(department);

                    return departmentConverter.toDto(created);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

}
