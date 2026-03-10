package br.org.kinflasy.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.dto.CalendarEventDto;
import br.org.kinflasy.dto.CalendarEventRequest;
import br.org.kinflasy.entities.CalendarEvent;
import br.org.kinflasy.repositories.CalendarEventRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CalendarEventService {

    private final ModelMapper mapper;

    private final CalendarEventRepository repository;

    /*
     * ACESSO PÚBLICO
     */

    @PostFilter("@fgau.withCaracteristics('calendar_event', filterObject.id, 'can_view')")
    public List<CalendarEventDto> listByUnitInRange(final UUID unitId, final LocalDateTime start,
            final LocalDateTime end) {
        return repository.findByUnitIdAndStartDateTimeBeforeAndEndDateTimeAfter(unitId, start, end).stream()
                .map(entity -> mapper.map(entity, CalendarEventDto.class))
                .toList();
    }

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('department', #departmentId, 'can_view', 'user', principal.id)")
    @PostFilter("@fgau.withCaracteristics('calendar_event', filterObject.id, 'can_view')")
    public List<CalendarEventDto> listByDepartmentInRange(final UUID departmentId, final LocalDateTime start,
            final LocalDateTime end) {
        return repository.findByDepartmentIdAndStartDateTimeBeforeAndEndDateTimeAfter(departmentId, start, end).stream()
                .map(entity -> mapper.map(entity, CalendarEventDto.class))
                .toList();
    }

    @PreAuthorize("@fga.check('unit', #unitId, '')")
    public CalendarEventDto create(final UUID unitId, final CalendarEventRequest request) {
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new IllegalArgumentException("A data de início deve ser antes da data do fim");
        }

        final var entity = mapper.map(request, CalendarEvent.class);
        entity.setId(null);

        final var saved = repository.save(entity);

        // TODO Publicar

        return mapper.map(saved, CalendarEventDto.class);
    }

}
