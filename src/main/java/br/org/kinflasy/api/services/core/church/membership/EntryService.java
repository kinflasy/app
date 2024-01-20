package br.org.kinflasy.api.services.core.church.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.church.membership.EntryDTO;
import br.org.kinflasy.api.entities.core.church.membership.Entry;
import br.org.kinflasy.api.repositories.core.church.membership.EntryRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class EntryService extends BaseService<EntryRepository, EntryDTO, Entry, Integer> {

    protected EntryService(@Autowired final EntryRepository repository) {
        super(repository);
    }

    @Override
    public @NonNull Integer getId(final @NonNull Entry entry) {
        return entry.getId();
    }

    @Override
    public @Nullable EntryDTO toNullableDTO(final @Nullable Entry entry) {
        return EntryDTO.ofNullable(entry);
    }

    @Override
    public @NonNull EntryDTO toNonNullDTO(final @NonNull Entry entry) {
        return EntryDTO.ofNonNull(entry);
    }

}
