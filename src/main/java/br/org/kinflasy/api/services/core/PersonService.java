package br.org.kinflasy.api.services.core;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.dto.core.PersonDTO;
import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.repositories.core.PersonRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(@Autowired final PersonRepository repository) {
        this.repository = repository;
    }

    public @NonNull Integer getId(final @NonNull Person person) {
        return person.getId();
    }

    public @Nullable PersonDTO toNullableDTO(final @Nullable Person item) {
        return PersonDTO.ofNullable(item);
    }

    public @NonNull PersonDTO toNonNullDTO(@NonNull Person item) {
        return PersonDTO.ofNonNull(item);
    }

    public @NonNull List<Person> findAll() {
        return repository.findAll();
    }
    
    private @NonNull Boolean existsById(final @NonNull Integer id) {
        return (id != null) ? repository.existsById(id) : false;
    }

    public @NonNull Boolean exists(final @NonNull Person item) {
        return existsById(getId(item));
    }

    public @NonNull Person findById(final @NonNull Integer id) throws EntityNotFoundException {
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


}
