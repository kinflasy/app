package br.org.kinflasy.api.dto.core.church;


import br.org.kinflasy.api.dto.core.contact.CreateAddress;
import lombok.Getter;

@Getter
public class CreateStarterChurch extends CreateChurch {

    private CreateAddress address;

    public CreateStarterChurch(final String name, final String slug, final String acronym,
            final String phone, final String email, final CreateAddress address) {
        super(name, slug, acronym, phone, email);
        this.address = address;
    }

}
