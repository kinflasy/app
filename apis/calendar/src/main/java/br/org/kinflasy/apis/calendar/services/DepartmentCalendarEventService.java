package br.org.kinflasy.apis.calendar.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.calendar.entities.DepartmentCalendarEvent;
import br.org.kinflasy.apis.calendar.repositories.DepartmentCalendarEventRepository;
import br.org.kinflasy.libs.calendar.dto.CalendarEventDto;
import br.org.kinflasy.libs.calendar.dto.CalendarEventRequest;
import br.org.kinflasy.libs.calendar.dto.DepartmentCalendarEventDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentCalendarEventService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final DepartmentCalendarEventRepository repository;

    private final CalendarEventService calendarEventService;

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('department', #departmentId, 'can_view', 'user', principal.id)")
    @PostFilter("@fgau.withCharacteristics('calendar_event', filterObject.id, 'can_view')")
    public List<DepartmentCalendarEventDto> listInRange(final UUID departmentId, final LocalDateTime start,
            final LocalDateTime end) {
        return repository.findByDepartmentIdInRange(departmentId, start, end).stream()
                .map(entity -> mapper.map(entity, DepartmentCalendarEventDto.class))
                .sorted((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()))
                .toList();
    }

    @PreAuthorize("@fga.check('department', #departmentId, 'can_observe', 'user', principal.id)")
    @PostFilter("@fgau.withCharacteristics('calendar_event', filterObject.id, 'can_view')")
    public List<CalendarEventDto> listInRangeWithCollabs(final UUID departmentId, final LocalDateTime start,
            final LocalDateTime end) {
        final var ownedEvents = listInRange(departmentId, start, end);
        final var collabEvents = calendarEventService.listCollaborationsInRange(departmentId, start, end);

        return Stream.concat(ownedEvents.stream(), collabEvents.stream())
                .sorted((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()))
                .toList();
    }

    @Transactional
    @PreAuthorize("@fga.check('department', #departmentId, 'can_manage', 'user', principal.id)")
    public DepartmentCalendarEventDto create(final UUID departmentId, final CalendarEventRequest request) {
        // Validar datas
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new IllegalArgumentException("A data de início deve ser antes da data do fim");
        }

        // Construir entidade
        final var entity = mapper.map(request, DepartmentCalendarEvent.class);
        entity.setId(null);
        entity.setDepartmentId(departmentId);

        // Salvar
        final var saved = repository.save(entity);
        final var dto = mapper.map(saved, DepartmentCalendarEventDto.class);

        // Replicar regras de visibilidade (o event handler vai cadastrar no FGA)
        dto.setVisibilityRules(request.getVisibilityRules());

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

}
