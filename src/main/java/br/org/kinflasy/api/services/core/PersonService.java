package br.org.kinflasy.api.services.core;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.repositories.core.PersonRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(@Autowired final PersonRepository repository) {
        this.repository = repository;
    }

    public @NonNull List<Person> findAll() {
        return repository.findAll();
    }

    @Transactional
    public @NonNull Person create(@NonNull final Person person) {
        return repository.save(person);
    }

    public @NonNull Person findById(@NonNull final Integer id) throws EntityNotFoundException {
        try {
            final var result = repository.findById(id).get();

            if (result == null) {
                throw new EntityNotFoundException("ID não encontrado");
            }

            return result;
        } catch (final NoSuchElementException | EntityNotFoundException e) {
            throw new EntityNotFoundException("ID não encontrado");
        }
    }

    public @NonNull Boolean exists(@NonNull final Person person) {
        return repository.existsById(person.getId());
    }

    @Transactional
    public @NonNull Person update(@NonNull final Person person) throws EntityNotFoundException {
        if (exists(person)) {
            return repository.save(person);
        }

        throw new EntityNotFoundException("ID não encontrado");
    }

    @Transactional
    public void delete(@NonNull final Person person) throws EntityNotFoundException {
        if (exists(person)) {
            repository.delete(person);
        } else {
            throw new EntityNotFoundException("ID não encontrado");
        }
    }

}
