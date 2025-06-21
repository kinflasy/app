package br.org.kinflasy.api.services.core.church.membership;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Integer getId(final Entry entry) {
        return entry.getId();
    }

    @Override
    public EntryDTO toNullableDTO(final Entry entry) {
        return EntryDTO.ofNullable(entry);
    }

    @Override
    public EntryDTO toNonNullDTO(final Entry entry) {
        return EntryDTO.ofNonNull(entry);
    }

}
