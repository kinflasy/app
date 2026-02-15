package br.org.kinflasy.apis.churches.services.department;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.converters.department.DepartmentConverter;
import br.org.kinflasy.apis.churches.entities.department.ExtensionSubscription;
import br.org.kinflasy.apis.churches.repositories.department.DepartmentRepository;
import br.org.kinflasy.apis.churches.repositories.department.ExtensionSubscriptionRepository;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionDto;
import br.org.kinflasy.libs.churches.dto.departments.ExtensionSubscriptionRequest;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationRequest;
import br.org.kinflasy.libs.churches.enums.department.Extension;
import br.org.kinflasy.libs.churches.events.department.ExtensionEvent;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class DepartmentService {

    private static final String NOT_FOUND_MESSAGE = "Departamento não encontrado";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final DepartmentRepository repository;
    private final DepartmentConverter converter;

    private final IntegrationService integrationService;

    private final ExtensionSubscriptionRepository subscriptionRepository;

    public List<DepartmentDto> listByUnitId(final UUID unitId) {
        return repository.findByUnitId(unitId).stream()
                .map(converter::toDto)
                .toList();
    }

    public DepartmentDto create(final UUID unitId, final DepartmentRequest request) {
        // Salvar regra de visibilidade
        // final var visibility =
        // peopleFilterClient.findOrCreate(request.getVisibility());

        // Construir departamento
        final var department = converter.toEntity(request);
        // department.setVisibilityId(visibility.getId());

        // Associar unidade
        department.setUnitId(unitId);

        // Salvar
        final var created = repository.saveAndFlush(department);

        // Gerar DTO
        final var dto = converter.toDto(created);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

    public Optional<DepartmentDto> findById(final UUID id) {
        return repository.findById(id)
                .map(converter::toDto);
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

    public IntegrationDto addIntegrant(final UUID id, final IntegrationRequest request) {
        return repository.findById(id)
                .map(ignoredDepartment -> integrationService.create(id, request))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public List<Extension> listExtensions(final UUID id) {
        return subscriptionRepository.findByDepartmentId(id).stream()
                .map(ExtensionSubscription::getExtension)
                .toList();
    }

    public ExtensionSubscriptionDto subscribeToExtension(final UUID id, final ExtensionSubscriptionRequest request) {
        final var entity = mapper.map(request, ExtensionSubscription.class);
        entity.setDepartmentId(id);

        final var saved = subscriptionRepository.save(entity);

        // Gerar DTO
        final var dto = mapper.map(saved, ExtensionSubscriptionDto.class);

        // Publicar evento
        publisher.publishEvent(new ExtensionEvent.Subscribed(dto));

        return dto;
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
