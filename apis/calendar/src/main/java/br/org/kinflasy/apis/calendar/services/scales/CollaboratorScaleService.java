package br.org.kinflasy.apis.calendar.services.scales;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.calendar.entities.scales.CollaboratorScale;
import br.org.kinflasy.apis.calendar.repositories.EventCollaborationRepository;
import br.org.kinflasy.apis.calendar.repositories.scales.CollaboratorScaleRepository;
import br.org.kinflasy.libs.calendar.dto.scales.CollaboratorScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CollaboratorScaleService {

    private final CollaboratorScaleRepository repository;
    private final EventCollaborationRepository collaborationRepository;

    private final ApplicationEventPublisher publisher;

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('department', #departmentId, 'can_observe', 'user', principal.id) and "
            + "@fga.check('calendar_event', #calendarEventId, 'can_observe', 'user', principal.id)")
    public List<CollaboratorScaleDto> listByCalendarEventIdAndDepartmentId(final UUID calendarEventId,
            final UUID departmentId) {
        return repository.findByCalendarEventIdAndDepartmentId(calendarEventId, departmentId).stream()
                .map(this::toDto)
                .toList();
    }

    @PreAuthorize("@fga.check('department', #departmentId, 'can_manage', 'user', principal.id) and "
            + "@fga.check('calendar_event', #calendarEventId, 'can_scale', 'user', principal.id)")
    public Optional<CollaboratorScaleDto> create(final UUID calendarEventId, final UUID departmentId,
            final ScaleRequest request) {
        return collaborationRepository.findByCalendarEventIdAndDepartmentId(calendarEventId, departmentId)
                .map(collab -> {
                    // Construir entidade
                    final var entity = new CollaboratorScale();
                    entity.setCollaborationId(collab.getId());
                    entity.setLineupId(request.getLineupId());

                    // Salvar
                    final var saved = repository.save(entity);
                    final var dto = toDto(saved);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Created<>(dto));

                    return toDto(saved);
                });
    }

    /*
     * ACESSO PRIVADO
     */

    private CollaboratorScaleDto toDto(final CollaboratorScale entity) {
        final var dto = new CollaboratorScaleDto();
        dto.setCollaborationId(entity.getCollaborationId())
                .setId(entity.getId())
                .setLineupId(entity.getLineupId());
        return dto;
    }

}
