package br.org.kinflasy.api.dto.core.church;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.Church;

public record ChurchDTO(
        @NonNull Integer id,
        @NonNull String name,
        @NonNull String slug,
        @Nullable String phone,
        @NonNull String email) {

    public static @Nullable ChurchDTO ofNullable(final @Nullable Church church) {
        return (church != null) ? ofNonNull(church) : null;
    }
    
    public static @NonNull ChurchDTO ofNonNull(final @NonNull Church church) {
        return new ChurchDTO(church.getId(), church.getName(), church.getSlug(), church.getPhone(), church.getEmail());
    }

}
