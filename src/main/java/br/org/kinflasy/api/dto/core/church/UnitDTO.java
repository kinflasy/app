package br.org.kinflasy.api.dto.core.church;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.utils.enums.core.church.UnitType;

public record UnitDTO(
        @NonNull Integer id,
        @NonNull String name,
        @NonNull String slug,
        @NonNull String email,
        @Nullable String phone,
        @NonNull UnitType type,
        @NonNull ChurchDTO church,
        @Nullable AddressDTO address) {

    public static @Nullable UnitDTO ofNullable(final @Nullable Unit unit) {
        return (unit != null) ? ofNonNull(unit) : null;
    }

    public static @NonNull UnitDTO ofNonNull(final @NonNull Unit unit) {
        return new UnitDTO(unit.getId(), unit.getName(), unit.getSlug(), unit.getEmail(), unit.getPhone(),
                unit.getType(), ChurchDTO.ofNonNull(unit.getChurch()), AddressDTO.ofNullable(unit.getAddress()));
    }

}
