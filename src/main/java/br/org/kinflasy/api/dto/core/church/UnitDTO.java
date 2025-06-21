package br.org.kinflasy.api.dto.core.church;


import java.util.UUID;

import br.org.kinflasy.api.dto.core.contact.AddressDTO;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.utils.enums.core.church.UnitType;

public record UnitDTO(
        UUID id,
        String name,
        String slug,
        String email,
        String phone,
        UnitType type,
        ChurchDTO church,
        AddressDTO address) {

    public static UnitDTO ofNullable(final Unit unit) {
        return (unit != null) ? ofNonNull(unit) : null;
    }

    public static UnitDTO ofNonNull(final Unit unit) {
        return new UnitDTO(unit.getId(), unit.getName(), unit.getSlug(), unit.getEmail(), unit.getPhone(),
                unit.getType(), ChurchDTO.ofNonNull(unit.getChurch()), AddressDTO.ofNullable(unit.getAddress()));
    }

}
