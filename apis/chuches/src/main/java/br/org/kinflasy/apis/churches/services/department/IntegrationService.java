package br.org.kinflasy.apis.churches.services.department;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.entities.department.Integration;
import br.org.kinflasy.apis.churches.repositories.department.IntegrationRepository;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationDto;
import br.org.kinflasy.libs.churches.dto.departments.IntegrationRequest;
import br.org.kinflasy.libs.churches.events.department.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class IntegrationService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final IntegrationRepository repository;

    public List<IntegrationDto> listByDepartment(final UUID departmentId) {
        return repository.findByDepartmentId(departmentId).stream()
                .map(integration -> mapper.map(integration, IntegrationDto.class))
                .toList();
    }

    public List<IntegrationDto> listByMembership(final UUID membershipId) {
        return repository.findByMembershipId(membershipId).stream()
                .map(integration -> mapper.map(integration, IntegrationDto.class))
                .toList();
    }

    public IntegrationDto create(final UUID departmentId, final IntegrationRequest request) {
        // Construir entidade
        final var entity = mapper.map(request, Integration.class);
        entity.setId(null);
        entity.setDepartmentId(departmentId);

        // Salvar
        final var saved = repository.save(entity);

        // Gerar DTO
        final var dto = mapper.map(saved, IntegrationDto.class);

        // Publicar evento
        publisher.publishEvent(new IntegrationEvent.Created(dto));

        return dto;
    }

    public Optional<IntegrationDto> findIntegration(final UUID departmentId, final UUID personId) {
        return repository.findByDepartmentIdAndMembershipId(departmentId, personId).stream()
                .findFirst()
                .map(integration -> mapper.map(integration, IntegrationDto.class));
    }

}
