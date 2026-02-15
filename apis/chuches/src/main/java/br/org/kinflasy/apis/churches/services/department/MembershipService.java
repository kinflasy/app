package br.org.kinflasy.apis.churches.services.department;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class MembershipService {

    private static final String NOT_FOUND_MESSAGE = "Relação de membresia não encontrada";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final MembershipRepository repository;

    @PreAuthorize("#personId.equals(principal.id)")
    public List<MembershipDto> findByPersonId(final UUID personId) {
        return repository.findByPersonId(personId).stream()
                .map(entity -> mapper.map(entity, MembershipDto.class))
                .toList();
    }

    @PreAuthorize("@fga.check('membership', id, 'admin', 'user', principal.id) or #personId.equals(principal.id)")
    public MembershipDto update(final UUID id, final MembershipRequest request) {
        return repository.findById(id)
                .map(entity -> {
                    // Gerar DTO original
                    final var original = mapper.map(entity, MembershipDto.class);

                    // Editar entidade
                    mapper.map(request, entity);

                    // Salvar
                    final var saved = repository.save(entity);

                    // Gerar DTO modificado
                    final var modified = mapper.map(saved, MembershipDto.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Updated<>(original, modified));

                    return modified;
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@fga.check('membership', id, 'admin', 'user', principal.id) or #personId.equals(principal.id)")
    public MembershipDto changePerson(final UUID id, final UUID personId) {
        return repository.findById(id)
                .map(entity -> {
                    // Gerar requisição com base no original
                    final var request = mapper.map(entity, MembershipRequest.class);

                    // Trocar ID da pessoa
                    request.setPersonId(personId);

                    // Salvar
                    return update(id, request);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

}
