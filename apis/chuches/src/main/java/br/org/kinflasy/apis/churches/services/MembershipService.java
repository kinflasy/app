package br.org.kinflasy.apis.churches.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.clients.PersonClient;
import br.org.kinflasy.apis.churches.entities.Membership;
import br.org.kinflasy.apis.churches.entities.PendingMembership;
import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.apis.churches.repositories.PendingMembershipRepository;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import br.org.kinflasy.libs.churches.dto.MembershipDto.Pending;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class MembershipService {

    private static final String NOT_FOUND_MESSAGE = "Relação de membresia não encontrada";
    private static final String NOT_FOUND_PENDING_MESSAGE = "Solicitação de membresia não encontrada";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;
    private final AuthUtils authUtils;

    private final MembershipRepository repository;
    private final PendingMembershipRepository pendingRepository;

    private final UnitService unitService;
    private final PersonClient personClient;

    /*
     * ACESSO AUTENTICADO
     */

    @Transactional
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
        entity.setUserConfirmationDate(LocalDateTime.now());

        return processSavedPending(pendingRepository.save(entity));
    }

    public List<Pending> listPendingForLoggedUser() {
        final var loggedUser = authUtils.getLoggedUser();
        return listPendingByPersonId(loggedUser.getId());
    }

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id) and "
            + "@fga.check('person_data', #request.personId, 'can_edit', 'user', principal.id)")
    public MembershipDto.Simple create(final UUID unitId, final MembershipRequest request) {
        final var entity = mapper.map(request, Membership.class);
        entity.setId(null);
        entity.setUnitId(unitId);

        final var saved = repository.save(entity);
        log.info("Membro de id {} adicionado à unidade de id {}", saved.getPersonId(), saved.getUnitId());

        // Publicar evento de membresia
        final var dto = mapper.map(saved, MembershipDto.Simple.class);
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        // Gerar DTO de retorno
        return dto;
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
        entity.setUnitConfirmationDate(LocalDateTime.now());

        return processSavedPending(pendingRepository.save(entity));
    }

    @PreAuthorize("@fga.check('person_data', #personId, 'can_view', 'user', principal.id) or #personId.equals(principal.id)")
    public List<MembershipDto.DetailingUnit> listByPersonId(final UUID personId) {
        return repository.findByPersonId(personId).stream()
                .map(entity -> {
                    return unitService.findById(entity.getUnitId())
                            .map(detailed -> {
                                // Trazer dados da pessoa
                                final var personDto = personClient.findById(personId).getBody();

                                // Construir DTO de membresia detalhada
                                final var membershipDto = new MembershipDto.DetailingUnit();
                                membershipDto.setUnit(detailed).setPerson(personDto);
                                mapper.map(entity, membershipDto);

                                return membershipDto;
                            });
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public Optional<MembershipDto.Detailed> findById(final UUID id) {
        return repository.findById(id)
                .map(entity -> {
                    final var unitDto = unitService.findById(entity.getUnitId())
                            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
                    final var personDto = personClient.findById(entity.getPersonId()).getBody();

                    final var dto = new MembershipDto.Detailed();
                    dto.setUnit(unitDto)
                            .setPerson(personDto);
                    mapper.map(entity, dto);

                    return dto;
                });
    }

    @PreAuthorize("@fga.check('membership', #id, 'can_edit', 'user', principal.id) or #personId.equals(principal.id)")
    public MembershipDto.Simple update(final UUID id, final MembershipRequest request) {
        return repository.findById(id)
                .map(entity -> {
                    // Gerar DTO original
                    final var original = mapper.map(entity, MembershipDto.Simple.class);

                    // Editar entidade
                    mapper.map(request, entity);
                    entity.setId(id);

                    // Salvar
                    final var saved = repository.save(entity);

                    // Gerar DTO modificado
                    final var modified = mapper.map(saved, MembershipDto.Simple.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Updated<>(original, modified));

                    return modified;
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@fga.check('membership', #id, 'can_edit', 'user', principal.id) or #personId.equals(principal.id)")
    public MembershipDto.Simple changePerson(final UUID id, final UUID personId) {
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

    @PreAuthorize("#personId.equals(principal.id)")
    public List<Pending> listPendingByPersonId(final UUID personId) {
        return pendingRepository.findByPersonId(personId).stream()
                .map(entity -> mapper.map(entity, Pending.class))
                .toList();
    }

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id)")
    public List<Pending> listPendingByUnitId(final UUID unitId) {
        return pendingRepository.findByUnitId(unitId).stream()
                .map(entity -> mapper.map(entity, Pending.class))
                .toList();
    }

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id)")
    public Pending updatePending(final UUID unitId, final MembershipRequest request) {
        return pendingRepository.findByUnitIdAndPersonId(unitId, request.getPersonId())
                .map(entity -> {
                    // Capturar objeto original
                    final var original = mapper.map(entity, Pending.class);

                    // Modificar
                    mapper.map(request, entity);
                    entity.setId(original.getId());

                    // Salvar
                    final var saved = pendingRepository.save(entity);
                    final var modified = mapper.map(saved, Pending.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Updated<>(original, modified));

                    // Criar, se confirmado por ambas as partes
                    createIfComplete(saved);

                    return modified;
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_PENDING_MESSAGE));
    }

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id)")
    public Pending confirmAsUnit(final UUID unitId, final UUID personId) {
        return pendingRepository.findByUnitIdAndPersonId(unitId, personId)
                .map(pending -> {
                    if (pending.getAffiliation() == null) {
                        throw new IllegalStateException("A filiação não pode estar vazia");
                    }

                    return confirm(pending, p -> p.setUnitConfirmationDate(LocalDateTime.now()));
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_PENDING_MESSAGE));
    }

    @PreAuthorize("#personId.equals(principal.id)")
    public Pending confirmAsPerson(final UUID unitId, final UUID personId) {
        return pendingRepository.findByUnitIdAndPersonId(unitId, personId)
                .map(pending -> confirm(pending, p -> p.setUserConfirmationDate(LocalDateTime.now())))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_PENDING_MESSAGE));
    }

    @PreAuthorize("@fga.check('unit', #unitId, 'admin', 'user', principal.id) or #personId.equals(principal.id)")
    public void reject(final UUID unitId, final UUID personId) {
        pendingRepository.findByUnitIdAndPersonId(unitId, personId)
                .ifPresent(pendingRepository::delete);
    }

    private Pending processSavedPending(final PendingMembership saved) {
        log.info("Solicitação realizada para membro de id {} ingressar na unidade de id {}",
                saved.getPersonId(), saved.getUnitId());

        // Gerar DTO de retorno
        final var dto = mapper.map(saved, Pending.class);

        // Publicar evento de membresia pendente
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

    private Pending confirm(final PendingMembership pending, final Consumer<PendingMembership> setter) {
        // Capturar objeto original
        final var original = mapper.map(pending, Pending.class);

        // Atualizar confirmação
        setter.accept(pending);
        final var saved = pendingRepository.save(pending);
        final var modified = mapper.map(saved, Pending.class);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Updated<>(original, modified));

        // Criar, se confirmado por ambas as partes
        createIfComplete(saved);

        return modified;
    }

    private Optional<MembershipDto.Simple> createIfComplete(final PendingMembership pending) {
        // Se ambas as partes confirmarem...
        if (pending.getUnitConfirmationDate() != null && pending.getUserConfirmationDate() != null) {
            // ... excluir pendente
            pendingRepository.findByUnitIdAndPersonId(pending.getUnitId(), pending.getPersonId())
                    .ifPresent(pendingRepository::delete);

            // ... criar oficial
            final var request = mapper.map(pending, MembershipRequest.class);
            return Optional.of(create(pending.getUnitId(), request));
        } else {
            return Optional.empty();
        }
    }

}
