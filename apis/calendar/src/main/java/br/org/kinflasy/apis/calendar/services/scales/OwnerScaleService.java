package br.org.kinflasy.apis.calendar.services.scales;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.calendar.entities.scales.OwnerScale;
import br.org.kinflasy.apis.calendar.repositories.scales.OwnerScaleRepository;
import br.org.kinflasy.libs.calendar.dto.scales.OwnerScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OwnerScaleService {

    private final OwnerScaleRepository repository;

    private final ApplicationEventPublisher publisher;

    public List<OwnerScaleDto> listByCalendarEventId(final UUID calendarEventId) {
        return repository.findByCalendarEventId(calendarEventId).stream()
                .map(this::toDto)
                .toList();
    }

    public OwnerScaleDto create(final UUID calendarEventId, final ScaleRequest request) {
        // Construir entidade
        final var entity = new OwnerScale();
        entity.setCalendarEventId(calendarEventId);
        entity.setLineupId(request.getLineupId());

        // Salvar
        final var saved = repository.save(entity);
        final var dto = toDto(saved);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return toDto(saved);
    }

    private OwnerScaleDto toDto(final OwnerScale entity) {
        final var dto = new OwnerScaleDto();
        dto.setCalendarEventId(entity.getCalendarEventId())
                .setId(entity.getId())
                .setLineupId(entity.getLineupId());
        return dto;
    }

}
