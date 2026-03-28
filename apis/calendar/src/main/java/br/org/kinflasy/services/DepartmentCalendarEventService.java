package br.org.kinflasy.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.dto.DepartmentCalendarEventDto;
import br.org.kinflasy.dto.DepartmentCalendarEventRequest;
import br.org.kinflasy.entities.DepartmentCalendarEvent;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.repositories.DepartmentCalendarEventRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentCalendarEventService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final DepartmentCalendarEventRepository repository;

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('department', #departmentId, 'can_view', 'user', principal.id)")
    @PostFilter("@fgau.withCaracteristics('calendar_event', filterObject.id, 'can_view')")
    public List<DepartmentCalendarEventDto> listInRange(final UUID departmentId, final LocalDateTime start,
            final LocalDateTime end) {
        return repository.findByStartDateTimeBeforeAndEndDateTimeAfter(departmentId, start, end).stream()
                .map(entity -> mapper.map(entity, DepartmentCalendarEventDto.class))
                .toList();
    }

    @PreAuthorize("@fga.check('department', #request.departmentId, 'can_manage', 'user', principal.id)")
    public DepartmentCalendarEventDto create(final UUID departmentId, final DepartmentCalendarEventRequest request) {
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new IllegalArgumentException("A data de início deve ser antes da data do fim");
        }

        final var entity = mapper.map(request, DepartmentCalendarEvent.class);
        entity.setId(null);
        entity.setDepartmentId(departmentId);

        final var saved = repository.save(entity);
        final var dto = mapper.map(saved, DepartmentCalendarEventDto.class);

        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

}
