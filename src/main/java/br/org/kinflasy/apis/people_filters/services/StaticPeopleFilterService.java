package br.org.kinflasy.apis.people_filters.services;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people_filters.entities.StaticPeopleFilter;
import br.org.kinflasy.apis.people_filters.repositories.StaticPeopleFilterRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StaticPeopleFilterService {

    private final StaticPeopleFilterRepository repository;

    public StaticPeopleFilter findOrCreate(final StaticPeopleFilter filter) {
        return repository.findOrCreate(filter);
    }

}
