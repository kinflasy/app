package br.org.kinflasy.api.dto.core.church;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.utils.enums.core.church.UnitType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUnit {

    private @NonNull String name;
    private @NonNull String slug;
    private @Nullable String phone;
    private @NonNull String email;
    private @NonNull UnitType type;
    private @NonNull CreateAddress address;

    public @NonNull Unit update(final @NonNull Unit unit) {
        unit.setName(name);
        unit.setSlug(slug);
        unit.setPhone(phone);
        unit.setEmail(email);
        unit.setType(type);
        unit.setAddress(address.toAddress());

        return unit;
    }

    public @NonNull Unit toUnit() {
        return update(new Unit());
    }

    public @NonNull Unit toUnit(final @NonNull Church church) {
        final var unit = update(new Unit());
        unit.setChurch(church);
        return unit;
    }

}
