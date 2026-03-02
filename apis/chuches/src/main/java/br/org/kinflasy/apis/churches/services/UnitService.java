package br.org.kinflasy.apis.churches.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.clients.AddressClient;
import br.org.kinflasy.apis.churches.clients.InactivePersonClient;
import br.org.kinflasy.apis.churches.clients.PersonClient;
import br.org.kinflasy.apis.churches.converters.UnitConverter;
import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.apis.churches.repositories.UnitRepository;
import br.org.kinflasy.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import br.org.kinflasy.libs.churches.dto.MembershipSimpleDto;
import br.org.kinflasy.libs.churches.dto.MembershipSimpleDto.Pending;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import br.org.kinflasy.libs.people.dto.InactivePersonRequest;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people.dto.PersonSimpleDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class UnitService {

    private static final String NOT_FOUND_MESSAGE = "Unidade não encontrada";

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final UnitRepository repository;
    private final UnitConverter converter;

    private final AddressClient addressClient;
    private final PersonClient personClient;
    private final InactivePersonClient inactivePersonClient;

    private final DepartmentService departmentService;
    private final MembershipService membershipService;
    private final MembershipRepository membershipRepository;

    /*
     * ACESSO PÚBLICO
     */

    public List<UnitDto> listByChurchId(final UUID churchId) {
        return repository.findByChurchId(churchId).stream()
                .map(converter::toDto)
                .toList();
    }

    public Optional<UnitDto> findById(final UUID id) {
        log.info("Buscando unidade de id {}...", id);
        return repository.findById(id).map(converter::toDto);
    }

    public Pending askToJoinUnit(final UUID id) {
        if (repository.existsById(id)) {
            return membershipService.askToJoinUnit(id);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    public List<DepartmentDto> listDepartments(final UUID id) {
        log.info("Listando todos os departamentos da unidade de id {}...", id);

        if (repository.existsById(id)) {
            return departmentService.listByUnitId(id);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    /*
     * ACESSO RESTRITO
     */

    @PreAuthorize("@fga.check('church', #churchId, 'admin', 'user', principal.id)")
    public UnitDto create(final UUID churchId, final UnitRequest request) {
        log.info("Criando unidade /{}...", request.getSlug());

        // Construir unidade
        final var unit = converter.toEntity(request);

        // Criar e associar endereço
        final var address = addressClient.create(request.getAddress());
        unit.setAddressId(address.getId());

        // Associar Igreja
        unit.setChurchId(churchId);
        log.info("Unidade /{} criada", request.getSlug());

        // Salvar
        final var created = repository.save(unit);

        // Criar DTO
        final var dto = converter.toDto(created);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id)")
    public UnitDto update(final UUID id, final UnitRequest request) {
        log.info("Atualizando unidade /{} (id {})...", request.getSlug(), id);
        return repository.findById(id)
                .map(original -> {
                    final var modified = converter.toEntity(request, original);

                    final var address = addressClient.update(original.getAddressId(), request.getAddress());
                    modified.setAddressId(address.getId());

                    repository.save(modified);
                    return converter.toDto(modified);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id)")
    public void delete(final UUID id) {
        log.info("Deletando unidade de id {}...", id);
        repository.findById(id)
                .ifPresentOrElse(
                        unit -> {
                            // Excluir departamentos
                            departmentService.listByUnitId(id)
                                    .forEach(department -> departmentService.delete(department.getId()));

                            // Excluir endereço
                            addressClient.delete(unit.getAddressId());

                            // Excluir unidade
                            repository.delete(unit);
                        },
                        () -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id)")
    public DepartmentDto createDepartment(final UUID id, final DepartmentRequest request) {
        return repository.findById(id)
                .map(ignoredUnit -> departmentService.create(id, request))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id)")
    public List<MembershipSimpleDto> listMembersAndExMembers(final UUID id) {
        return membershipRepository.findByUnitId(id).stream()
                .map(membership -> mapper.map(membership, MembershipSimpleDto.class))
                .toList();
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id)")
    public List<MembershipDto> listMembersAndExMembersWithDetails(final UUID id) {
        return listMembersAndExMembers(id).stream()
                .map(simpleDto -> mapper.map(simpleDto, MembershipDto.class)
                        .setPerson(mapper.map(personClient.findById(simpleDto.getPersonId()), PersonSimpleDto.class)))
                .toList();
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id) or @fga.check('person_data', #personId, 'can_view', 'user', principal.id)")
    public Optional<MembershipSimpleDto> findActiveMembership(final UUID id, final UUID personId) {
        return membershipRepository.findByUnitIdAndPersonIdAndLeaveDateNull(id, personId)
                .map(membership -> mapper.map(membership, MembershipSimpleDto.class));
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id)")
    public List<MembershipSimpleDto> listMembers(final UUID id) {
        return membershipRepository.findByUnitIdAndLeaveDateNull(id).stream()
                .map(membership -> mapper.map(membership, MembershipSimpleDto.class))
                .toList();
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id)")
    public List<MembershipDto> listMembersWithDetails(final UUID id) {
        return listMembers(id).stream()
                .map(simpleDto -> {
                    PersonDto byId = personClient.findById(simpleDto.getPersonId());
                    return mapper.map(simpleDto, MembershipDto.class)
                            .setPerson(mapper.map(byId, PersonSimpleDto.class));
                })
                .toList();
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id)")
    public MembershipDto registerMember(final UUID id, final MembershipRequest.Register request) {
        // Obter dados da unidade
        return repository.findById(id)
                .map(unit -> {
                    // Criar pessoa inativa
                    final var personRequest = mapper
                            .map(request.getPerson(), InactivePersonRequest.class)
                            .setChurchId(unit.getChurchId());
                    final var savedPerson = inactivePersonClient.create(personRequest);

                    // Gerar requisição de membresia
                    final var membershipRequest = mapper.map(request, MembershipRequest.class);
                    membershipRequest.setPersonId(savedPerson.getId());

                    return addMember(id, membershipRequest);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@fga.check('unit', #id, 'admin', 'user', principal.id)")
    public void removeMember(final UUID id, final UUID personId) {
        membershipRepository.findByUnitIdAndPersonId(id, personId)
                .forEach(membership -> {
                    if (membership.getLeaveDate() != null) {
                        membershipRepository.delete(membership);
                    }
                });
    }

    /*
     * VERIFICAÇÃO DE ACESSO REDIRECIONADA
     */

    public MembershipDto addMember(final UUID id, final MembershipRequest request) {
        if (repository.existsById(id)) {
            return membershipService.create(id, request);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    public Pending askForUserToJoin(final UUID id, final MembershipRequest request) {
        if (repository.existsById(id)) {
            return membershipService.askForUserToJoin(id, request);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
        }
    }

}
