package br.org.kinflasy.api.apis.people.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.apis.people.repositories.PersonRepository;
import br.org.kinflasy.api.dto.core.PersonDto;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(@Autowired final PersonRepository repository) {
        this.repository = repository;
    }

    public UUID getId(final Person person) {
        return person.getId();
    }

    public PersonDto toNullableDTO(final Person item) {
        return PersonDto.ofNullable(item);
    }

    public PersonDto toNonNullDTO(Person item) {
        return PersonDto.ofNonNull(item);
    }

    public List<Person> findAll() {
        return repository.findAll();
    }
    
    private Boolean existsById(final UUID id) {
        return (id != null) ? repository.existsById(id) : false;
    }

    public Boolean exists(final Person item) {
        return existsById(getId(item));
    }

    public Person findById(final UUID id) throws EntityNotFoundException {
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
