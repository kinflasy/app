package br.org.kinflasy.api.dto.core.people_filter;


import java.util.UUID;

import br.org.kinflasy.api.entities.core.people_filter.StaticPeopleFilter;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;

public record StaticPeopleFilterDTO(
        UUID id,
        PersonCharacteristic characteristic) {

    public static StaticPeopleFilterDTO ofNullable(final StaticPeopleFilter filter) {
        return (filter != null) ? ofNonNull(filter) : null;
    }

    public static StaticPeopleFilterDTO ofNonNull(final StaticPeopleFilter filter) {
        return new StaticPeopleFilterDTO(filter.getId(), filter.getCharacteristic());
    }

}
