package br.org.kinflasy.apis.churches.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.converters.UnitConverter;
import br.org.kinflasy.apis.churches.repositories.UnitRepository;
import br.org.kinflasy.apis.churches.services.department.DepartmentService;
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
    private final DepartmentService departmentService;

    public List<UnitDto> listByChurchId(final UUID churchId) {
        return repository.findByChurchId(churchId).stream()
                .map(converter::toDto)
                .toList();
    }

    public UnitDto create(final UUID churchId, final UnitRequest request) {
        // Construir unidade
        final var unit = converter.toEntity(request);

        // Criar e associar endereço
        final var address = addressClient.create(request.getAddress());
        unit.setAddressId(address.getId());

        // Associar Igreja
        unit.setChurchId(churchId);

        // Salvar
        final var created = repository.save(unit);

        return converter.toDto(created);
    }

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
                .ifPresentOrElse(
                        unit -> {
                            // Excluir departamentos
                            departmentService.listByUnitId(id)
                                    .forEach(department -> departmentService.delete(department.getId()));

                            // Excluir endereço
                            addressClient.delete(unit.getAddressId());

                            // Excluir unidade
                            repository.delete(unit);
                        },
                        () -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public List<DepartmentDto> listDepartments(final UUID id) {
        return repository.findById(id)
                .map(ignoredUnit -> departmentService.listByUnitId(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public DepartmentDto createDepartment(final UUID id, final DepartmentRequest request) {
        return repository.findById(id)
                .map(ignoredUnit -> departmentService.create(id, request))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

}
