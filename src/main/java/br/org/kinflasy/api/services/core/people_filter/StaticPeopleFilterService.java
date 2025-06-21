package br.org.kinflasy.api.services.core.people_filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.people_filter.StaticPeopleFilterDTO;
import br.org.kinflasy.api.entities.core.people_filter.StaticPeopleFilter;
import br.org.kinflasy.api.repositories.core.people_filter.StaticPeopleFilterRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class StaticPeopleFilterService
        extends BaseService<StaticPeopleFilterRepository, StaticPeopleFilterDTO, StaticPeopleFilter, Integer> {

    protected StaticPeopleFilterService(@Autowired final StaticPeopleFilterRepository repository) {
        super(repository);
    }

    @Override
    public @NonNull Integer getId(final @NonNull StaticPeopleFilter staticpeoplefilter) {
        return staticpeoplefilter.getId();
    }

    @Override
    public @Nullable StaticPeopleFilterDTO toNullableDTO(final @Nullable StaticPeopleFilter staticpeoplefilter) {
        return StaticPeopleFilterDTO.ofNullable(staticpeoplefilter);
    }

    @Override
    public @NonNull StaticPeopleFilterDTO toNonNullDTO(final @NonNull StaticPeopleFilter staticpeoplefilter) {
        return StaticPeopleFilterDTO.ofNonNull(staticpeoplefilter);
    }

    public @NonNull StaticPeopleFilter findOrCreate(final @NonNull StaticPeopleFilter filter) {
        return repository.findOrCreate(filter);
    }

}
