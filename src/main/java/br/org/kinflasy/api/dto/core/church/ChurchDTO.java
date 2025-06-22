package br.org.kinflasy.api.dto.core.church;


import java.util.UUID;

import br.org.kinflasy.api.apis.churches.entities.Church;

public record ChurchDTO(
        UUID id,
        String name,
        String slug,
        String acronym,
        String phone,
        String email) {

    public static ChurchDTO ofNullable(final Church church) {
        return (church != null) ? ofNonNull(church) : null;
    }

    public static ChurchDTO ofNonNull(final Church church) {
        return new ChurchDTO(church.getId(), church.getName(), church.getSlug(), church.getAcronym(), church.getPhone(),
                church.getEmail());
    }

}
