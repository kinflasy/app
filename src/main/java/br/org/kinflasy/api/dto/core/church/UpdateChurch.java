package br.org.kinflasy.api.dto.core.church;

import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.Church;

public record UpdateChurch(
        String name,
        @Nullable String slug,
        @Nullable String acronym,
        @Nullable String phone,
        @Nullable String email) {

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
