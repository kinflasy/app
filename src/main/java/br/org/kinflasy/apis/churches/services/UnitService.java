package br.org.kinflasy.apis.churches.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.converters.UnitConverter;
import br.org.kinflasy.apis.churches.entities.Membership;
import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.apis.churches.repositories.UnitRepository;
import br.org.kinflasy.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.apis.people.services.InactivePersonService;
import br.org.kinflasy.apis.people.services.PersonService;
import br.org.kinflasy.clients.AddressClient;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import br.org.kinflasy.libs.churches.dto.MembershipSimpleDto;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
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

    private final UnitRepository repository;
    private final UnitConverter converter;

    // TODO trocar por client
    private final PersonService personService;
    private final InactivePersonService inactivePersonService;

    private final AddressClient addressClient;
    private final DepartmentService departmentService;
    private final MembershipRepository membershipRepository;

    public List<UnitDto> listByChurchId(final UUID churchId) {
        return repository.findByChurchId(churchId).stream()
                .map(converter::toDto)
                .toList();
    }

    @PreAuthorize("@churchSecurityService.canCreateUnit(#churchId, principal)")
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

        return converter.toDto(created);
    }

    public Optional<UnitDto> findById(final UUID id) {
        log.info("Buscando unidade de id {}...", id);
        return repository.findById(id).map(converter::toDto);
    }

    @PreAuthorize("@churchSecurityService.isIntegrantOfSomaInChurch(#churchId, principal)")
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

    @PostFilter("@churchSecurityService.matchesCondition(filterObject.getVisibilityId(), principal)")
    public List<DepartmentDto> listDepartments(final UUID id) {
        log.info("Listando todos os departamentos da unidade de id {}...", id);
        return repository.findById(id)
                .map(ignoredUnit -> departmentService.listByUnitId(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@churchSecurityService.isIntegrantOfSomaInUnit(#id, principal)")
    public DepartmentDto createDepartment(final UUID id, final DepartmentRequest request) {
        return repository.findById(id)
                .map(ignoredUnit -> departmentService.create(id, request))
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    @PreAuthorize("@churchSecurityService.isIntegrantOfSomaInUnit(#id, principal)")
    public List<MembershipSimpleDto> listMembers(final UUID id) {
        return membershipRepository.findByUnitId(id).stream()
                .map(membership -> mapper.map(membership, MembershipSimpleDto.class))
                .toList();
    }

    @PreAuthorize("@churchSecurityService.isIntegrantOfSomaInUnit(#id, principal)")
    public List<MembershipDto> listMembersWithDetails(final UUID id) {
        return listMembers(id).stream()
                .map(simpleDto -> mapper.map(simpleDto, MembershipDto.class)
                        .setPerson(personService.findById(simpleDto.getPersonId())
                                .map(personDto -> mapper.map(personDto, PersonSimpleDto.class))
                                .get()))
                .toList();
    }

    @PreAuthorize("@churchSecurityService.isIntegrantOfSomaInUnit(#id, principal)")
    public Optional<MembershipSimpleDto> findActiveMembership(final UUID id, final UUID personId) {
        return membershipRepository.findByUnitIdAndPersonId(id, personId).stream()
                .filter(membership -> membership.getLeaveDate() == null)
                .map(membership -> mapper.map(membership, MembershipSimpleDto.class))
                .findFirst();
    }

    @PreAuthorize("@churchSecurityService.isIntegrantOfSomaInUnit(#id, principal)")
    public MembershipSimpleDto addMember(final UUID id, final MembershipRequest request) {
        log.info("Adicionando membro de id {} à unidade de id {}...", request.getPersonId(), id);
        final var entity = mapper.map(request, Membership.class);
        entity.setId(null);
        entity.setUnitId(id);

        final var saved = membershipRepository.save(entity);
        log.info("Membro de id {} adicionado à unidade de id {}", request.getPersonId(), id);

        return mapper.map(saved, MembershipSimpleDto.class);
    }

    @PreAuthorize("@churchSecurityService.isIntegrantOfSomaInUnit(#id, principal)")
    public List<MembershipSimpleDto> addMembers(final UUID id, final List<MembershipRequest> request) {
        final var entities = request.stream().map(member -> {
            final var entity = mapper.map(member, Membership.class);
            entity.setId(null);
            entity.setUnitId(id);

            return entity;
        })
                .toList();

        final var saved = membershipRepository.saveAll(entities);

        return saved.stream()
                .map(membership -> mapper.map(membership, MembershipSimpleDto.class))
                .toList();
    }

    @PreAuthorize("@churchSecurityService.isIntegrantOfSomaInUnit(#id, principal)")
    public List<MembershipSimpleDto> registerMembers(final UUID id, final List<MembershipRequest.Register> request) {
        final var entities = request.stream().map(member -> {
            final var savedPerson = inactivePersonService.create(member.getPerson());

            final var entity = mapper.map(member, Membership.class);
            entity.setId(null);
            entity.setUnitId(id);
            entity.setPersonId(savedPerson.getId());

            return entity;
        })
                .toList();

        final var saved = membershipRepository.saveAll(entities);

        return saved.stream()
                .map(membership -> mapper.map(membership, MembershipSimpleDto.class))
                .toList();
    }

    @PreAuthorize("@churchSecurityService.isIntegrantOfSomaInUnit(#id, principal)")
    public void removeMember(final UUID id, final UUID personId) {
        membershipRepository.findByUnitIdAndPersonId(id, personId)
                .forEach(membership -> {
                    if (membership.getLeaveDate() != null) {
                        membershipRepository.delete(membership);
                    }
                });
    }

}
