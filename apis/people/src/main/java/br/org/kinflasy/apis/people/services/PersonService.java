package br.org.kinflasy.apis.people.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people.entities.InactivePerson;
import br.org.kinflasy.apis.people.entities.Person;
import br.org.kinflasy.apis.people.entities.User;
import br.org.kinflasy.apis.people.repositories.PersonRepository;
import br.org.kinflasy.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.PersonIdentifierDto;
import br.org.kinflasy.libs.people.dto.UserDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {

    private final ModelMapper mapper;

    private final PersonRepository repository;

    /*
     * ACESSO PÚBLICO
     */

    public boolean exists(final Person person) {
        return Optional.ofNullable(person)
                .map(Person::getId)
                .map(repository::existsById)
                .orElse(false);
    }

    public Optional<PersonIdentifierDto> identifyById(final UUID id) {
        return repository.findById(id)
                .map(entity -> mapper.map(entity, PersonIdentifierDto.class));
    }

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('person_data', #id, 'can_view', 'user', principal.id)")
    public Optional<PersonDto> findById(final UUID id) throws EntityNotFoundException {
        return repository.findById(id)
                .map(person -> switch (person) {
                    case User user -> mapper.map(user, UserDto.class);
                    case InactivePerson inactivePerson -> mapper.map(inactivePerson, InactivePersonDto.class);
                    default -> throw new IllegalStateException("Não foi possível mapear o tipo de pessoa: " + person.getClass());
                });
    }

}
