package br.org.kinflasy.api.apis.people.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.apis.people.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public List<Person> findAll() {
        return repository.findAll();
    }

    public boolean exists(final Person item) {
        final var id = item.getId();
        return id != null && repository.existsById(id);
    }

    public Person findById(final UUID id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

}
