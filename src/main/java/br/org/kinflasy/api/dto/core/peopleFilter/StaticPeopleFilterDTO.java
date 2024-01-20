package br.org.kinflasy.api.dto.core.peopleFilter;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.peopleFilter.StaticPeopleFilter;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;

public record StaticPeopleFilterDTO(
        @NonNull Integer id,
        @NonNull PersonCharacteristic characteristic) {

    public static @Nullable StaticPeopleFilterDTO ofNullable(@Nullable final StaticPeopleFilter filter) {
        return (filter != null) ? ofNonNull(filter) : null;
    }

    public static @NonNull StaticPeopleFilterDTO ofNonNull(final @NonNull StaticPeopleFilter filter) {
        return new StaticPeopleFilterDTO(filter.getId(), filter.getCharacteristic());
    }

}
