package br.org.kinflasy.apis.people.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people.entities.Person;
import br.org.kinflasy.apis.people.repositories.PersonRepository;
import br.org.kinflasy.libs.people.dto.PersonDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {

    private final ModelMapper mapper;

    private final PersonRepository repository;

    public List<Person> findAll() {
        return repository.findAll();
    }

    public boolean exists(final Person item) {
        final var id = item.getId();
        return id != null && repository.existsById(id);
    }

    public Optional<PersonDto> findById(final UUID id) throws EntityNotFoundException {
        return repository.findById(id)
                .map(person -> mapper.map(person, PersonDto.class));
    }

}
