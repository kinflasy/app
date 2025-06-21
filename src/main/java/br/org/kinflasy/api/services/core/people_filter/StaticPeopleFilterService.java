package br.org.kinflasy.api.services.core.people_filter;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.people_filter.StaticPeopleFilterDTO;
import br.org.kinflasy.api.entities.core.people_filter.StaticPeopleFilter;
import br.org.kinflasy.api.repositories.core.people_filter.StaticPeopleFilterRepository;
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
    public StaticPeopleFilterDTO toNullableDTO(final StaticPeopleFilter staticpeoplefilter) {
        return StaticPeopleFilterDTO.ofNullable(staticpeoplefilter);
    }

    @Override
    public StaticPeopleFilterDTO toNonNullDTO(final StaticPeopleFilter staticpeoplefilter) {
        return StaticPeopleFilterDTO.ofNonNull(staticpeoplefilter);
    }

    public StaticPeopleFilter findOrCreate(final StaticPeopleFilter filter) {
        return repository.findOrCreate(filter);
    }

}
