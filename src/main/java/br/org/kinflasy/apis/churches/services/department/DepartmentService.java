package br.org.kinflasy.apis.churches.services.department;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.converters.department.DepartmentConverter;
import br.org.kinflasy.apis.churches.entities.department.ExtensionSubscription;
import br.org.kinflasy.apis.churches.repositories.department.DepartmentRepository;
import br.org.kinflasy.apis.churches.repositories.department.ExtensionSubscriptionRepository;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionRequest;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentService {

    private static final String NOT_FOUND_MESSAGE = "Departamento não encontrado";

    private final ModelMapper mapper;

    private final DepartmentRepository repository;
    private final DepartmentConverter converter;

    private final ExtensionSubscriptionRepository subscriptionRepository;

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
        final var created = repository.saveAndFlush(department);

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

    public List<Extension> listExtensions(final UUID id) {
        return subscriptionRepository.findByDepartmentId(id).stream()
                .map(ExtensionSubscription::getExtension)
                .toList();
    }

    public ExtensionSubscriptionDto associateExtension(final UUID id, final ExtensionSubscriptionRequest request) {
        final var entity = mapper.map(request, ExtensionSubscription.class);
        entity.setDepartmentId(id);

        final var saved = subscriptionRepository.save(entity);

        return mapper.map(saved, ExtensionSubscriptionDto.class);
    }

    public Optional<ExtensionSubscriptionDto> findExtension(final UUID id, final Extension extension) {
        return subscriptionRepository.findByDepartmentIdAndExtension(id, extension)
                .map(subscription -> mapper.map(subscription, ExtensionSubscriptionDto.class));
    }

    public void dissociateExtension(final UUID id, final ExtensionSubscriptionRequest request) {
        subscriptionRepository.findByDepartmentIdAndExtension(id, request.getExtension())
                .ifPresent(subscriptionRepository::delete);
    }

}
