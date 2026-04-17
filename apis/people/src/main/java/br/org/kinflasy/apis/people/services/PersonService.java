package br.org.kinflasy.apis.people.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.org.kinflasy.apis.people.clients.MediaClient;
import br.org.kinflasy.apis.people.entities.InactivePerson;
import br.org.kinflasy.apis.people.entities.Person;
import br.org.kinflasy.apis.people.entities.User;
import br.org.kinflasy.apis.people.repositories.PersonRepository;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.media.validators.ProfileImageValidator;
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
    private final AuthUtils authUtils;

    private final PersonRepository repository;

    private final MediaClient mediaClient;

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
                    default -> throw new IllegalStateException(
                            "Não foi possível mapear o tipo de pessoa: " + person.getClass());
                });
    }

    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public Optional<PersonDto> updateProfileImage(final UUID id, final MultipartFile file) {
        return repository.findById(id)
                .map(person -> {
                    // Validar a imagem
                    ProfileImageValidator.validate(file);

                    // Fazer upload da nova foto
                    final var uploaded = mediaClient.upload(file).getBody();

                    // Deletar a foto antiga, se existir
                    Optional.ofNullable(person.getProfileImageId())
                            .ifPresent(mediaClient::delete);

                    // Atualizar a referência da foto no banco de dados
                    person.setProfileImageId(uploaded.getId());
                    final var saved = repository.save(person);

                    // Mapear a entidade atualizada para DTO
                    return switch (saved) {
                        case User user -> mapper.map(user, UserDto.class);
                        case InactivePerson inactivePerson -> mapper.map(inactivePerson, InactivePersonDto.class);
                        default -> throw new IllegalStateException(
                                "Não foi possível mapear o tipo de pessoa: " + saved.getClass());
                    };
                });
    }

    @PreAuthorize("isAuthenticated()")
    public Optional<PersonDto> updateProfileImage(final MultipartFile file) {
        final var loggedUser = authUtils.getLoggedUser();
        return updateProfileImage(loggedUser.getId(), file);
    }

    @PreAuthorize("@fga.check('person_data', #id, 'can_edit', 'user', principal.id)")
    public Optional<PersonDto> deleteProfileImage(final UUID id) {
        return repository.findById(id)
                .map(person -> {
                    // Deletar a foto antiga, se existir
                    Optional.ofNullable(person.getProfileImageId())
                            .ifPresent(mediaClient::delete);

                    // Remover a referência da foto no banco de dados
                    person.setProfileImageId(null);
                    final var saved = repository.save(person);

                    // Mapear a entidade atualizada para DTO
                    return switch (saved) {
                        case User user -> mapper.map(user, UserDto.class);
                        case InactivePerson inactivePerson -> mapper.map(inactivePerson, InactivePersonDto.class);
                        default -> throw new IllegalStateException(
                                "Não foi possível mapear o tipo de pessoa: " + saved.getClass());
                    };
                });
    }

    @PreAuthorize("isAuthenticated()")
    public Optional<PersonDto> deleteProfileImage() {
        final var loggedUser = authUtils.getLoggedUser();
        return deleteProfileImage(loggedUser.getId());
    }

}
