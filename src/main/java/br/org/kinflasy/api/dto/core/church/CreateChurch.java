package br.org.kinflasy.api.dto.core.church;


import br.org.kinflasy.api.entities.core.church.Church;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateChurch {

    private String name;
    private String slug;
    private String acronym;
    private String phone;
    private String email;

    public Church update(final Church church) {
        church.setName(name);
        church.setSlug(slug);
        church.setPhone(phone);
        church.setAcronym(acronym);
        church.setEmail(email);

        return church;
    }

    public Church toChurch() {
        return update(new Church());
    }

}
