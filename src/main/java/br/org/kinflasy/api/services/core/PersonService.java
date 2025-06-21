package br.org.kinflasy.api.services.core;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Integer getId(final Person person) {
        return person.getId();
    }

    public PersonDTO toNullableDTO(final Person item) {
        return PersonDTO.ofNullable(item);
    }

    public PersonDTO toNonNullDTO(Person item) {
        return PersonDTO.ofNonNull(item);
    }

    public List<Person> findAll() {
        return repository.findAll();
    }
    
    private Boolean existsById(final Integer id) {
        return (id != null) ? repository.existsById(id) : false;
    }

    public Boolean exists(final Person item) {
        return existsById(getId(item));
    }

    public Person findById(final Integer id) throws EntityNotFoundException {
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
