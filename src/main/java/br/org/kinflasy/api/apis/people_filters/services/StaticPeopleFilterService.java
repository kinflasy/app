package br.org.kinflasy.api.apis.people_filters.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.people_filters.entities.StaticPeopleFilter;
import br.org.kinflasy.api.apis.people_filters.repositories.StaticPeopleFilterRepository;
import br.org.kinflasy.api.dto.core.people_filter.StaticPeopleFilterDTO;
import br.org.kinflasy.api.services.BaseService;

@Service
public class StaticPeopleFilterService
        extends BaseService<StaticPeopleFilterRepository, StaticPeopleFilterDTO, StaticPeopleFilter, UUID> {

    protected StaticPeopleFilterService(@Autowired final StaticPeopleFilterRepository repository) {
        super(repository);
    }

    @Override
    public UUID getId(final StaticPeopleFilter staticpeoplefilter) {
        return staticpeoplefilter.getId();
    }

    @Override
    public StaticPeopleFilterDTO toDto(final StaticPeopleFilter staticpeoplefilter) {
        return StaticPeopleFilterDTO.ofNonNull(staticpeoplefilter);
    }

    public StaticPeopleFilter findOrCreate(final StaticPeopleFilter filter) {
        return repository.findOrCreate(filter);
    }

}
