package br.org.kinflasy.api.dto.core.church;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.Church;

public record StarterChurchDTO(
        @NonNull Integer id,
        @NonNull String name,
        @NonNull String slug,
        @Nullable String acronym,
        @Nullable String phone,
        @NonNull String email,
        @NonNull List<UnitWithDepartmentsDTO> units) {

    public static @Nullable StarterChurchDTO ofNullable(final @Nullable Church church) {
        return (church != null) ? ofNonNull(church) : null;
    }

    public static @NonNull StarterChurchDTO ofNonNull(final @NonNull Church church) {
        var units = new ArrayList<>(church.getUnits().stream().map(UnitWithDepartmentsDTO::ofNonNull).toList());

        return new StarterChurchDTO(church.getId(), church.getName(), church.getSlug(), church.getAcronym(),
                church.getPhone(), church.getEmail(), units);
    }

}
