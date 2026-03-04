package br.org.kinflasy.apis.churches.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.churches.services.MembershipService;
import br.org.kinflasy.apis.churches.services.UnitService;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import br.org.kinflasy.libs.churches.dto.MembershipSimpleDto;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import br.org.kinflasy.libs.churches.dto.MembershipSimpleDto.Pending;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/church/units")
@Tag(name = "Unit")
@AllArgsConstructor
public class UnitController {

    private final UnitService service;
    private final MembershipService membershipService;

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma unidade pelo ID.")
    public ResponseEntity<UnitDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de uma unidade.")
    public ResponseEntity<UnitDto> update(@PathVariable final UUID id,
            @RequestBody final UnitRequest request) {
        try {
            return ResponseEntity.ok(service.update(id, request));
        } catch (final EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar uma unidade, removendo-a do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable final UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (final EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("{id}/departments")
    @Operation(summary = "Listar departamentos", description = "Listar os departamentos de uma unidade.")
    public ResponseEntity<List<DepartmentDto>> listDepartments(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.listDepartments(id));
    }

    @PostMapping("{id}/departments")
    @Operation(summary = "Cadastrar departamento", description = "Cadastrar um novo departamento em uma unidade.")
    public ResponseEntity<DepartmentDto> createDepartment(@PathVariable final UUID id,
            @RequestBody @Valid final DepartmentRequest request) {
        return new ResponseEntity<>(service.createDepartment(id, request), HttpStatus.CREATED);
    }

    @GetMapping("{id}/members")
    @Operation(summary = "Listar membros", description = "Listar os membros de uma unidade.")
    public ResponseEntity<List<MembershipDto>> listMembers(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.listMembersWithDetails(id));
    }

    @GetMapping("{id}/members-and-ex-members")
    @Operation(summary = "Listar membros", description = "Listar os membros ativos de uma unidade.")
    public ResponseEntity<List<MembershipDto>> listMembersAndExMembers(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.listMembersAndExMembersWithDetails(id));
    }

    @GetMapping("{id}/membership/{personId}")
    public ResponseEntity<MembershipSimpleDto> findActiveMembership(@PathVariable final UUID id,
            @PathVariable final UUID personId) {
        return service.findActiveMembership(id, personId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping("{id}/members")
    @Transactional
    @Operation(summary = "Associar membro", description = "Adicionar pessoa pré-existente como membro de uma unidade.")
    public ResponseEntity<MembershipDto> associateMember(@PathVariable final UUID id,
            @RequestBody @Valid final MembershipRequest request) {
        return ResponseEntity.ok(service.addMember(id, request));
    }

    @PostMapping("{id}/members/ask")
    @Transactional
    @Operation(summary = "Pedir para um usuário ingressar na unidade", description = "Solicitar que pessoa pré-existente seja membro de uma unidade.")
    public ResponseEntity<Pending> askForUserToJoin(@PathVariable final UUID id,
            @RequestBody @Valid final MembershipRequest request) {
        return ResponseEntity.ok(service.askForUserToJoin(id, request));
    }

    @PutMapping("{id}/pending-members")
    @Transactional
    @Operation(summary = "Pedir para um usuário ingressar na unidade", description = "Solicitar que pessoa pré-existente seja membro de uma unidade.")
    public Pending updatePending(@PathVariable final UUID id, final @RequestBody MembershipRequest request) {
        return membershipService.updatePending(id, request);
    }

    @PostMapping("{id}/member/{personId}/confirm")
    @Transactional
    @Operation(summary = "Confirmar solicitação de usuário para ingressar na unidade", description = "Confirmar solicitação de usuário para ingressar na unidade.")
    public ResponseEntity<Pending> confirmAsUnit(@PathVariable final UUID id, @PathVariable final UUID personId) {
        return ResponseEntity.ok(membershipService.confirmAsUnit(id, personId));
    }

    @PostMapping("{id}/join")
    @Transactional
    @Operation(summary = "Pedir para ingressar na unidade", description = "Solicitar que a administração de uma unidade permita o ingresso da pessoa logada.")
    public ResponseEntity<Pending> askToJoinUnit(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.askToJoinUnit(id));
    }

    @PostMapping("{id}/join/confirm")
    @Transactional
    @Operation(summary = "Confirmar solicitação para ingressar na unidade", description = "Confirmar solicitação para a pessoa logada ingressar na unidade.")
    public ResponseEntity<Pending> confirmAsUser(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.askToJoinUnit(id));
    }

    @PostMapping("{id}/member/{personId}/reject")
    @Transactional
    @Operation(summary = "Rejeitar solicitação de usuário para ingressar na unidade", description = "Rejeitar solicitação de usuário para ingressar na unidade.")
    public ResponseEntity<Pending> reject(@PathVariable final UUID id, @PathVariable final UUID personId) {
        membershipService.reject(id, personId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/members/pending")
    @Operation(summary = "Listar membros pendentes", description = "Listar as solicitações de ingresso em uma unidade.")
    public ResponseEntity<List<Pending>> listPendingMemberships(@PathVariable final UUID id) {
        return ResponseEntity.ok(membershipService.listPendingByUnitId(id));
    }

    @PostMapping("{id}/members/register")
    @Transactional
    @Operation(summary = "Cadastrar membros", description = "Cadastrar novas pessoas inativas e associá-las como membros de uma unidade.")
    public ResponseEntity<MembershipDto> registerMembers(@PathVariable final UUID id,
            @RequestBody final MembershipRequest.Register request) {
        return ResponseEntity.ok(service.registerMember(id, request));
    }

}
