package br.org.kinflasy.api.dto.core.church;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.Church;

public record StarterChurchDTO(
        Integer id,
        String name,
        String slug,
        @Nullable String acronym,
        @Nullable String phone,
        String email,
        List<UnitWithDepartmentsDTO> units) {

    public static @Nullable StarterChurchDTO ofNullable(final @Nullable Church church) {
        return (church != null) ? ofNonNull(church) : null;
    }

    public static StarterChurchDTO ofNonNull(final Church church) {
        var units = new ArrayList<>(church.getUnits().stream().map(UnitWithDepartmentsDTO::ofNonNull).toList());

        return new StarterChurchDTO(church.getId(), church.getName(), church.getSlug(), church.getAcronym(),
                church.getPhone(), church.getEmail(), units);
    }

}
