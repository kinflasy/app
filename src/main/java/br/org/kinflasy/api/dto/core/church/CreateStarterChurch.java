package br.org.kinflasy.api.dto.core.church;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import lombok.Getter;

@Getter
public class CreateStarterChurch extends CreateChurch {

    private @NonNull CreateAddress address;

    public CreateStarterChurch(final @NonNull String name, final @NonNull String slug, final @Nullable String acronym,
            final @Nullable String phone, final @NonNull String email, final @NonNull CreateAddress address) {
        super(name, slug, acronym, phone, email);
        this.address = address;
    }

}
