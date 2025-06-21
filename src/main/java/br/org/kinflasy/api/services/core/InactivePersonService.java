package br.org.kinflasy.api.services.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.InactivePersonDTO;
import br.org.kinflasy.api.entities.core.InactivePerson;
import br.org.kinflasy.api.repositories.core.InactivePersonRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class InactivePersonService extends BaseService<InactivePersonRepository, InactivePersonDTO, InactivePerson, Integer> {

    public InactivePersonService(@Autowired final InactivePersonRepository repository) {
        super(repository);
    }

    @Override
    public Integer getId(final InactivePerson person) {
        return person.getId();
    }

    @Override
    public InactivePersonDTO toNullableDTO(final InactivePerson item) {
        return InactivePersonDTO.ofNullable(item);
    }

    @Override
    public InactivePersonDTO toNonNullDTO(InactivePerson item) {
        return InactivePersonDTO.ofNonNull(item);
    }

}
