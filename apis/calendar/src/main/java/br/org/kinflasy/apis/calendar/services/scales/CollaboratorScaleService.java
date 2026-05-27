package br.org.kinflasy.apis.calendar.services.scales;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.calendar.entities.scales.CollaboratorScale;
import br.org.kinflasy.apis.calendar.repositories.scales.CollaboratorScaleRepository;
import br.org.kinflasy.libs.calendar.dto.scales.CollaboratorScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CollaboratorScaleService {

    private final CollaboratorScaleRepository repository;

    private final ApplicationEventPublisher publisher;

    public List<CollaboratorScaleDto> listByCollaborationId(final UUID collaborationId) {
        return repository.findByCollaborationId(collaborationId).stream()
                .map(this::toDto)
                .toList();
    }

    public CollaboratorScaleDto create(final UUID collaborationId, final ScaleRequest request) {
        // Construir entidade
        final var entity = new CollaboratorScale();
        entity.setCollaborationId(collaborationId);
        entity.setLineupId(request.getLineupId());

        // Salvar
        final var saved = repository.save(entity);
        final var dto = toDto(saved);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return toDto(saved);
    }

    private CollaboratorScaleDto toDto(final CollaboratorScale entity) {
        final var dto = new CollaboratorScaleDto();
        dto.setCollaborationId(entity.getCollaborationId())
                .setId(entity.getId())
                .setLineupId(entity.getLineupId());
        return dto;
    }

}
