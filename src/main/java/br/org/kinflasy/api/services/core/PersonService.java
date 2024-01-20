package br.org.kinflasy.api.services.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.PersonDTO;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.repositories.core.PersonRepository;
import br.org.kinflasy.api.services.BaseService;

@Service
public class PersonService extends BaseService<PersonRepository, PersonDTO, Person, Integer> {

    public PersonService(@Autowired final PersonRepository repository) {
        super(repository);
    }

    @Override
    public @NonNull Integer getId(final @NonNull Person person) {
        return person.getId();
    }

    @Override
    public @Nullable PersonDTO toNullableDTO(final @Nullable Person item) {
        return PersonDTO.ofNullable(item);
    }

    @Override
    public @NonNull PersonDTO toNonNullDTO(@NonNull Person item) {
        return PersonDTO.ofNonNull(item);
    }

}
