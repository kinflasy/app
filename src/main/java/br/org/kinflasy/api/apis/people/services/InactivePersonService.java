package br.org.kinflasy.api.apis.people.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.people.entities.InactivePerson;
import br.org.kinflasy.api.apis.people.repositories.InactivePersonRepository;
import br.org.kinflasy.api.dto.core.InactivePersonDTO;
import br.org.kinflasy.api.services.BaseService;

@Service
public class InactivePersonService extends BaseService<InactivePersonRepository, InactivePersonDTO, InactivePerson, UUID> {

    public InactivePersonService(@Autowired final InactivePersonRepository repository) {
        super(repository);
    }

    @Override
    public UUID getId(final InactivePerson person) {
        return person.getId();
    }

    @Override
    public InactivePersonDTO toDto(InactivePerson item) {
        return InactivePersonDTO.ofNonNull(item);
    }

}
