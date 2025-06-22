package br.org.kinflasy.api.dto.core.church;


import br.org.kinflasy.api.apis.churches.entities.Church;

public record UpdateChurch(
        String name,
        String slug,
        String acronym,
        String phone,
        String email) {

    public Church update(final Church church) {
        if (name != null) {
            church.setName(name);
        }

        if (slug != null) {
            church.setSlug(slug);
        }

        if (acronym != null) {
            church.setAcronym(acronym);
        }

        if (phone != null) {
            church.setPhone(phone);
        }

        if (email != null) {
            church.setEmail(email);
        }

        return church;
    }

}
