package br.org.kinflasy.api.services.core;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.repositories.core.PersonRepository;
import br.org.kinflasy.api.services.ServiceBase;

@Service
public class PersonService extends ServiceBase<Person, Integer, PersonRepository> {

    public PersonService(@Autowired final PersonRepository repository) {
        super(repository);
    }

    @Override
    public @NonNull Function<Person, Integer> getIdFunction() {
        return Person::getId;
    }

}
