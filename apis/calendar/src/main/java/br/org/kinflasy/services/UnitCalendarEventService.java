package br.org.kinflasy.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.clients.ChurchClient;
import br.org.kinflasy.dto.CalendarEventRequest;
import br.org.kinflasy.dto.UnitCalendarEventDto;
import br.org.kinflasy.entities.UnitCalendarEvent;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.repositories.UnitCalendarEventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnitCalendarEventService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final UnitCalendarEventRepository repository;

    private final ChurchClient churchClient;

    /*
     * ACESSO PÚBLICO
     */

    @PostFilter("@fgau.withCharacteristics('calendar_event', filterObject.id, 'can_view')")
    public List<UnitCalendarEventDto> listInRange(final UUID unitId, final LocalDateTime start,
            final LocalDateTime end) {
        return repository.findByUnitId(unitId, start, end).stream()
                .map(entity -> mapper.map(entity, UnitCalendarEventDto.class))
                .sorted((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()))
                .toList();
    }

    @PostFilter("@fgau.withCharacteristics('calendar_event', filterObject.id, 'can_view')")
    public List<UnitCalendarEventDto> listByChurchInRange(final UUID churchId, final LocalDateTime start,
            final LocalDateTime end) {
        return churchClient.listUnits(churchId).stream()
                .flatMap(unit -> listInRange(unit.getId(), start, end).stream())
                .sorted((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()))
                .toList();
    }

    /*
     * ACESSO RESTRITO
     */

    @Transactional
    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id)")
    public UnitCalendarEventDto create(final UUID unitId, final CalendarEventRequest request) {
        // Validar datas
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new IllegalArgumentException("A data de início deve ser antes da data do fim");
        }

        // Construir entidade
        final var entity = mapper.map(request, UnitCalendarEvent.class);
        entity.setId(null);
        entity.setUnitId(unitId);

        // Salvar
        final var saved = repository.save(entity);
        final var dto = mapper.map(saved, UnitCalendarEventDto.class);

        // Replicar regras de visibilidade (o event handler vai cadastrar no FGA)
        dto.setVisibilityRules(request.getVisibilityRules());

        // Publicar evento
        final var event = new EntityEvent.Created<>(dto);
        publisher.publishEvent(event);

        return dto;
    }

}
