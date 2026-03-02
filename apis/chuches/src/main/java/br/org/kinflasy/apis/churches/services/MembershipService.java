package br.org.kinflasy.apis.churches.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.entities.Membership;
import br.org.kinflasy.apis.churches.entities.PendingMembership;
import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.apis.churches.repositories.PendingMembershipRepository;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import br.org.kinflasy.libs.churches.dto.MembershipSimpleDto;
import br.org.kinflasy.libs.churches.dto.MembershipSimpleDto.Pending;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import jakarta.persistence.EntityExistsException;
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
    private final AuthUtils authUtils;

    private final MembershipRepository repository;
    private final PendingMembershipRepository pendingRepository;

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id) and @fga.check('person_data', #request.personId, 'can_edit', 'user', principal.id)")
    public MembershipDto create(final UUID unitId, final MembershipRequest request) {
        final var entity = mapper.map(request, Membership.class);
        entity.setId(null);
        entity.setUnitId(unitId);

        final var saved = repository.save(entity);
        log.info("Membro de id {} adicionado à unidade de id {}", saved.getPersonId(), saved.getUnitId());

        // Publicar evento de membresia
        final var dto = mapper.map(saved, MembershipDto.class);
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        // Gerar DTO de retorno
        return mapper.map(saved, MembershipDto.class);
    }

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id)")
    public Pending askForUserToJoin(final UUID unitId, final MembershipRequest request) {
        repository.findByUnitIdAndPersonIdAndLeaveDateNull(unitId, request.getPersonId())
                .ifPresent(existing -> {
                    throw new EntityExistsException(
                            "O usuário já é %s desta unidade".formatted(existing.getAffiliation()));
                });

        final var entity = mapper.map(request, PendingMembership.class);
        entity.setId(null);
        entity.setUnitId(unitId);
        entity.setUnitConfirmationDate(LocalDate.now());

        return processSavedPending(pendingRepository.save(entity));
    }

    public Pending askToJoinUnit(final UUID unitId) {
        final var loggedUser = authUtils.getLoggedUser();

        repository.findByUnitIdAndPersonIdAndLeaveDateNull(unitId, loggedUser.getId())
                .ifPresent(existing -> {
                    throw new EntityExistsException(
                            "Você já é %s desta unidade".formatted(existing.getAffiliation()));
                });

        final var entity = new PendingMembership();
        entity.setId(null);
        entity.setUnitId(unitId);
        entity.setPersonId(loggedUser.getId());
        entity.setUserConfirmationDate(LocalDate.now());

        return processSavedPending(pendingRepository.save(entity));
    }

    @PreAuthorize("@fga.check('person_data', #personId, 'can_view', 'user', principal.id) or #personId.equals(principal.id)")
    public List<MembershipDto> findByPersonId(final UUID personId) {
        return repository.findByPersonId(personId).stream()
                .map(entity -> mapper.map(entity, MembershipDto.class))
                .toList();
    }

    @PreAuthorize("@fga.check('membership', #id, 'can_edit', 'user', principal.id) or #personId.equals(principal.id)")
    public MembershipDto update(final UUID id, final MembershipRequest request) {
        return repository.findById(id)
                .map(entity -> {
                    // Gerar DTO original
                    final var original = mapper.map(entity, MembershipDto.class);

                    // Editar entidade
                    mapper.map(request, entity);
                    entity.setId(id);

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

    @PreAuthorize("@fga.check('membership', #id, 'can_edit', 'user', principal.id) or #personId.equals(principal.id)")
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

    private Pending processSavedPending(final PendingMembership saved) {
        log.info("Solicitação realizada para membro de id {} ingressar na unidade de id {}",
                saved.getPersonId(), saved.getUnitId());

        // Gerar DTO de retorno
        final var dto = mapper.map(saved, MembershipSimpleDto.Pending.class);

        // Publicar evento de membresia pendente
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

}
