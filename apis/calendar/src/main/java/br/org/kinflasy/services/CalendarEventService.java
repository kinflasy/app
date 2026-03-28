package br.org.kinflasy.services;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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
    


}

