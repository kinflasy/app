package br.org.kinflasy.api.apis.churches.services.membership;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.churches.entities.membership.Entry;
import br.org.kinflasy.api.apis.churches.repositories.membership.EntryRepository;
import br.org.kinflasy.api.dto.core.church.membership.EntryDTO;
import br.org.kinflasy.api.services.BaseService;

@Service
public class EntryService extends BaseService<EntryRepository, EntryDTO, Entry, UUID> {

    protected EntryService(@Autowired final EntryRepository repository) {
        super(repository);
    }

    @Override
    public UUID getId(final Entry entry) {
        return entry.getId();
    }

    @Override
    public EntryDTO toDto(final Entry entry) {
        return EntryDTO.ofNonNull(entry);
    }

}
