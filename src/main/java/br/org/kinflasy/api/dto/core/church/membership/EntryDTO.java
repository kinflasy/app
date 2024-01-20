package br.org.kinflasy.api.dto.core.church.membership;

import java.time.LocalDate;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import br.org.kinflasy.api.entities.core.church.membership.Entry;
import br.org.kinflasy.api.utils.enums.core.church.membership.EntryType;

public record EntryDTO(
        @NonNull Integer id,
        @NonNull MembershipDTO membership,
        @NonNull EntryType type,
        @NonNull LocalDate date) {

    public static @Nullable EntryDTO ofNullable(final @Nullable Entry entry) {
        return (entry != null) ? ofNonNull(entry) : null;
    }

    public static @NonNull EntryDTO ofNonNull(final @NonNull Entry entry) {
        return new EntryDTO(entry.getId(), MembershipDTO.ofNonNull(entry.getMembership()), entry.getType(),
                entry.getDate());
    }

}
