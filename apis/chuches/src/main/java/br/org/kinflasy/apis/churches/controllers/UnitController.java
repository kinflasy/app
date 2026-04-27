package br.org.kinflasy.apis.churches.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.kinflasy.apis.churches.services.MembershipService;
import br.org.kinflasy.apis.churches.services.UnitService;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.MembershipDto.Pending;
import br.org.kinflasy.libs.churches.dto.MembershipDto.SimplePending;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentRequest;
import br.org.kinflasy.libs.contacts.dto.LinkDto;
import br.org.kinflasy.libs.contacts.dto.LinkRequest;
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

    @GetMapping
    @Operation(summary = "Listar membresias ativas", description = "Listar relações de membresia que estejam válidas para o usuário logado.")
    public ResponseEntity<List<MembershipDto.DetailingUnit>> listByLoggedUser() {
        return ResponseEntity.ok(service.listByLoggedUser());
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma unidade pelo ID.")
    public ResponseEntity<UnitDto.Detailed> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping("{id}")
    @Operation(summary = "Editar", description = "Editar os dados de uma unidade.")
    public ResponseEntity<UnitDto> update(@PathVariable final UUID id,
            @RequestBody final UnitRequest request) {
        try {
            return ResponseEntity.ok(service.update(id, request));
        } catch (final EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "{id}/profile-image", consumes = "multipart/form-data")
    @Operation(summary = "Atualizar a imagem de perfil", description = "Atualizar a imagem de perfil da unidade.")
    public ResponseEntity<UnitDto> updateProfileImage(@PathVariable final UUID id,
            @RequestPart final MultipartFile file) {
        return service.updateProfileImage(id, file)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}/profile-image")
    @Operation(summary = "Deletar imagem de perfil", description = "Deletar a imagem de perfil de uma unidade.")
    public ResponseEntity<UnitDto> deleteProfileImage(@PathVariable final UUID id) {
        return service.deleteProfileImage(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping(value = "{id}/cover-image", consumes = "multipart/form-data")
    @Operation(summary = "Atualizar a imagem de capa", description = "Atualizar a imagem de capa da unidade.")
    public ResponseEntity<UnitDto> updateCoverImage(@PathVariable final UUID id,
            @RequestPart final MultipartFile file) {
        return service.updateCoverImage(id, file)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}/cover-image")
    @Operation(summary = "Deletar imagem de capa", description = "Deletar a imagem de capa de uma unidade.")
    public ResponseEntity<UnitDto> deleteCoverImage(@PathVariable final UUID id) {
        return service.deleteCoverImage(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}")
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
            @RequestBody @Valid final DepartmentRequest.WithRules request) {
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
    public ResponseEntity<MembershipDto.Simple> findActiveMembership(@PathVariable final UUID id,
            @PathVariable final UUID personId) {
        return service.findActiveMembership(id, personId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping("{id}/members")
    @Operation(summary = "Associar membro", description = "Adicionar pessoa pré-existente como membro de uma unidade.")
    public ResponseEntity<MembershipDto.Simple> associateMember(@PathVariable final UUID id,
            @RequestBody @Valid final MembershipRequest request) {
        return ResponseEntity.ok(service.addMember(id, request));
    }

    @PostMapping("{id}/members/invite")
    @Operation(summary = "Pedir para um usuário ingressar na unidade", description = "Solicitar que pessoa pré-existente seja membro de uma unidade.")
    public ResponseEntity<SimplePending> inviteUserToJoin(@PathVariable final UUID id,
            @RequestBody @Valid final MembershipRequest request) {
        return ResponseEntity.ok(service.inviteUserToJoin(id, request));
    }

    @PutMapping("{id}/pending-members")
    @Operation(summary = "Atualizar solicitação para usuário ingressar na unidade", description = "Atualizar dados da solicitação que pede para usuário ingressar na unidade.")
    public SimplePending updatePending(@PathVariable final UUID id, final @RequestBody MembershipRequest request) {
        return membershipService.updatePending(id, request);
    }

    @PostMapping("{id}/member/{personId}/confirm")
    @Operation(summary = "Confirmar solicitação de usuário para ingressar na unidade", description = "Confirmar solicitação de usuário para ingressar na unidade.")
    public ResponseEntity<SimplePending> confirmAsUnit(@PathVariable final UUID id, @PathVariable final UUID personId) {
        return ResponseEntity.ok(membershipService.confirmAsUnit(id, personId));
    }

    @PostMapping("{id}/join")
    @Operation(summary = "Pedir para ingressar na unidade", description = "Solicitar que a administração de uma unidade permita o ingresso da pessoa logada.")
    public ResponseEntity<MembershipDto.Simple> askToJoinUnit(@PathVariable final UUID id,
            @RequestBody final MembershipRequest.Join request) {
        return ResponseEntity.ok(service.askToJoinUnit(id, request));
    }

    @PostMapping("{id}/join/confirm")
    @Operation(summary = "Confirmar solicitação para ingressar na unidade", description = "Confirmar solicitação para a pessoa logada ingressar na unidade.")
    public ResponseEntity<SimplePending> confirmAsUser(@PathVariable final UUID id) {
        return ResponseEntity.ok(membershipService.confirmAsPerson(id));
    }

    @PostMapping("{id}/member/{personId}/reject")
    @Operation(summary = "Rejeitar solicitação de usuário para ingressar na unidade", description = "Rejeitar solicitação de usuário para ingressar na unidade.")
    public ResponseEntity<SimplePending> reject(@PathVariable final UUID id, @PathVariable final UUID personId) {
        membershipService.reject(id, personId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/members/pending")
    @Operation(summary = "Listar membros pendentes", description = "Listar as solicitações de ingresso em uma unidade.")
    public ResponseEntity<List<Pending>> listPendingMemberships(@PathVariable final UUID id) {
        return ResponseEntity.ok(membershipService.listPendingByUnitId(id));
    }

    @PostMapping("{id}/members/register")
    @Operation(summary = "Cadastrar membro", description = "Cadastrar nova pessoa inativa e associá-la como membro de uma unidade.")
    public ResponseEntity<MembershipDto.Simple> registerMembers(@PathVariable final UUID id,
            @RequestBody final MembershipRequest.Register request) {
        return ResponseEntity.ok(service.registerMember(id, request));
    }

    @GetMapping("{id}/links")
    @Operation(summary = "Listar links", description = "Listar os links associados a uma unidade.")
    public ResponseEntity<List<LinkDto>> listLinks(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.listLinks(id));
    }

    @PostMapping("{id}/links")
    @Operation(summary = "Cadastrar link", description = "Cadastrar um novo link e associá-lo a uma unidade.")
    public ResponseEntity<LinkDto> createLink(@PathVariable final UUID id, @RequestBody final LinkRequest request) {
        return ResponseEntity.ok(service.createLink(id, request));
    }

}
