package br.org.kinflasy.api.dto.core.church;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.Church;

public record ChurchDTO(
        Integer id,
        String name,
        String slug,
        @Nullable String acronym,
        @Nullable String phone,
        String email) {

    public static @Nullable ChurchDTO ofNullable(final @Nullable Church church) {
        return (church != null) ? ofNonNull(church) : null;
    }

    public static ChurchDTO ofNonNull(final Church church) {
        return new ChurchDTO(church.getId(), church.getName(), church.getSlug(), church.getAcronym(), church.getPhone(),
                church.getEmail());
    }

}
