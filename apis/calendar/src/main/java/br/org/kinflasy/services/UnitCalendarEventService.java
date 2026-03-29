package br.org.kinflasy.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.dto.UnitCalendarEventDto;
import br.org.kinflasy.dto.UnitCalendarEventRequest;
import br.org.kinflasy.entities.UnitCalendarEvent;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.repositories.UnitCalendarEventRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnitCalendarEventService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final UnitCalendarEventRepository repository;

    /*
     * ACESSO PÚBLICO
     */

    @PostFilter("@fgau.withCaracteristics('calendar_event', filterObject.id, 'can_view')")
    public List<UnitCalendarEventDto> listInRange(final UUID unitId, final LocalDateTime start,
            final LocalDateTime end) {
        return repository.findByUnitIdAndStartDateTimeBeforeAndEndDateTimeAfter(unitId, start, end).stream()
                .map(entity -> mapper.map(entity, UnitCalendarEventDto.class))
                .toList();
    }

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id)")
    public UnitCalendarEventDto create(final UUID unitId, final UnitCalendarEventRequest request) {
        // Validar datas
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new IllegalArgumentException("A data de início deve ser antes da data do fim");
        }

        // Construir entidade
        final var entity = mapper.map(request, UnitCalendarEvent.class);
        entity.setId(null);
        entity.setUnitId(unitId);

        // Salvar e publicar evento
        final var saved = repository.save(entity);
        final var dto = mapper.map(saved, UnitCalendarEventDto.class);

        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

}
