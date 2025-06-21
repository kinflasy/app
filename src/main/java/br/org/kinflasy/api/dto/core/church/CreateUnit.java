package br.org.kinflasy.api.dto.core.church;

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

    private String name;
    private String slug;
    private @Nullable String phone;
    private String email;
    private UnitType type;
    private CreateAddress address;

    public Unit update(final Unit unit) {
        unit.setName(name);
        unit.setSlug(slug);
        unit.setPhone(phone);
        unit.setEmail(email);
        unit.setType(type);
        unit.setAddress(address.toAddress());

        return unit;
    }

    public Unit toUnit() {
        return update(new Unit());
    }

    public Unit toUnit(final Church church) {
        final var unit = update(new Unit());
        unit.setChurch(church);
        return unit;
    }

}
