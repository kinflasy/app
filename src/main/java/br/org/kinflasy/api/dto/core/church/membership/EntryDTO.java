package br.org.kinflasy.api.dto.core.church.membership;

import java.time.LocalDate;
import java.util.UUID;

import br.org.kinflasy.api.entities.core.church.membership.Entry;
import br.org.kinflasy.api.utils.enums.core.church.membership.EntryType;

public record EntryDTO(
        UUID id,
        MembershipDTO membership,
        EntryType type,
        LocalDate date) {

    public static EntryDTO ofNullable(final Entry entry) {
        return (entry != null) ? ofNonNull(entry) : null;
    }

    public static EntryDTO ofNonNull(final Entry entry) {
        return new EntryDTO(entry.getId(), MembershipDTO.ofNonNull(entry.getMembership()), entry.getType(),
                entry.getDate());
    }

}
