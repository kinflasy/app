package br.org.kinflasy.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.dto.CalendarEventDto;
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

}
