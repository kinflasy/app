package br.org.kinflasy.api.services.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.repositories.core.PersonRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(@Autowired final PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person create(@NonNull final Person person) {
        return repository.save(person);
    }

    public Person findById(@NonNull final Integer id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    public Boolean exists(@NonNull final Person person) {
        return repository.existsById(person.getId());
    }
    
    public Person update(@NonNull final Person person) throws EntityNotFoundException {
        if (exists(person)) {
            return repository.save(person);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
    public void delete(@NonNull final Person person) throws EntityNotFoundException {
        if (exists(person)) {
            repository.delete(person);
        } 
        
        throw new EntityNotFoundException("ID não encontrado");
    }
    
}
