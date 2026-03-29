package br.org.kinflasy.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.dto.CalendarEventDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.repositories.CalendarEventRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CalendarEventService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final CalendarEventRepository repository;

    /*
     * ACESSO RESTRITO
     */
    @PreAuthorize("@fgau.withCaracteristics('calendar_event', #id, 'can_view')")
    public Optional<CalendarEventDto> findById(final UUID id) {
        return repository.findById(id)
                .map(entity -> mapper.map(entity, CalendarEventDto.class));
    }

    @PreAuthorize("@fga.check('calendar_event', #id, 'can_edit', 'user', principal.id)")
    public CalendarEventDto update(final UUID id, final CalendarEventDto request) {
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new IllegalArgumentException("A data de início deve ser antes da data do fim");
        }

        final var entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));
        final var original = mapper.map(entity, CalendarEventDto.class);

        mapper.map(request, entity);
        final var saved = repository.save(entity);
        final var dto = mapper.map(saved, CalendarEventDto.class);

        publisher.publishEvent(new EntityEvent.Updated<>(original, dto));

        return dto;
    }

    @PreAuthorize("@fga.check('calendar_event', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        repository.findById(id)
                .ifPresent(entity -> {
                    repository.delete(entity);
                    publisher.publishEvent(new EntityEvent.Deleted<>(entity));
                });
    }

}
