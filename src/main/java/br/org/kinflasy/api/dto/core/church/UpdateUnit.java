package br.org.kinflasy.api.dto.core.church;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.utils.enums.core.church.UnitType;

public record UpdateUnit(
        @NonNull String name,
        @NonNull String slug,
        @Nullable String phone,
        @NonNull String email,
        @NonNull UnitType type,
        @NonNull CreateAddress address) {

    public @NonNull Unit update(final @NonNull Unit unit) {

        if (name != null) {
            unit.setName(name);
        }

        if (slug != null) {
            unit.setSlug(slug);
        }

        if (phone != null) {
            unit.setPhone(phone);
        }

        if (email != null) {
            unit.setEmail(email);
        }

        if (type != null) {
            unit.setType(type);
        }

        if (address != null) {
            unit.setAddress(address.toAddress());
        }

        return unit;
    }

}
