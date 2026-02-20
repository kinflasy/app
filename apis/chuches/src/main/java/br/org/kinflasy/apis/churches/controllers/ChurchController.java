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

import br.org.kinflasy.apis.churches.services.ActivationUseCaseService;
import br.org.kinflasy.apis.churches.services.ChurchService;
import br.org.kinflasy.apis.churches.services.ChurchUseCaseService;
import br.org.kinflasy.apis.churches.services.DeactivationUseCaseService;
import br.org.kinflasy.apis.churches.services.UnitService;
import br.org.kinflasy.libs.churches.dto.ChurchDto;
import br.org.kinflasy.libs.churches.dto.ChurchRequest;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.UnitDto;
import br.org.kinflasy.libs.churches.dto.UnitRequest;
import br.org.kinflasy.libs.people.dto.ActivationRequest;
import br.org.kinflasy.libs.people.dto.DeactivationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/churches")
@Tag(name = "Church")
@AllArgsConstructor
public class ChurchController {

    private final ChurchService service;
    private final UnitService unitService;
    private final ChurchUseCaseService useCaseService;
    private final ActivationUseCaseService activationUseCaseService;
    private final DeactivationUseCaseService deactivationUseCaseService;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todas as igrejas cadastradas.")
    public ResponseEntity<List<ChurchDto>> listAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar", description = "Cadastrar uma igreja.")
    public ResponseEntity<ChurchDto> create(@RequestBody @Valid final ChurchRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @PostMapping("/starter")
    @Transactional
    @Operation(summary = "Cadastrar combo inicial", description = "Cadastrar uma igreja e sua primeira unidade.")
    public ResponseEntity<ChurchDto.Starter> createStarter(@RequestBody @Valid final ChurchRequest.Starter request) {
        return new ResponseEntity<>(useCaseService.createStarter(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma igreja pelo ID.")
    public ResponseEntity<ChurchDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de uma igreja.")
    public ResponseEntity<ChurchDto> update(@PathVariable final UUID id, @RequestBody final ChurchRequest request) {
        try {
            return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar uma igreja, removendo-a do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable final UUID id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("{id}/units")
    @Operation(summary = "Listar unidades", description = "Listar as unidades de uma igreja.")
    public ResponseEntity<List<UnitDto>> getUnits(@PathVariable final UUID id) {
        return new ResponseEntity<>(service.listUnits(id), HttpStatus.OK);
    }

    @PostMapping("{id}/units")
    @Operation(summary = "Cadastrar unidade", description = "Cadastrar uma nova unidade em uma igreja.")
    public ResponseEntity<UnitDto> createUnit(@PathVariable final UUID id,
            @RequestBody @Valid final UnitRequest request) {
        return new ResponseEntity<>(unitService.create(id, request), HttpStatus.CREATED);
    }

    @PostMapping("activate-member")
    @Operation(summary = "Ativar membro", description = "Substituir pessoa inativa por usuário ativo em todas as unidades.")
    public ResponseEntity<List<MembershipDto>> activateMember(@RequestBody final ActivationRequest request) {
        return ResponseEntity.ok(activationUseCaseService.activate(request.getInactivePersonId(), request.getUserId()));
    }

    @PostMapping("{id}/deactivate-member")
    @Operation(summary = "Desativar membro", description = "Substituir membresias do usuário ativo por pessoa inativa em todas as unidades dessa igreja.")
    public ResponseEntity<List<MembershipDto>> deactivateMember(@PathVariable final UUID id,
            @RequestBody final DeactivationRequest request) {
        return ResponseEntity.ok(deactivationUseCaseService.deactivateOne(id, request.getUserId()));
    }

    @PostMapping("deactivate-member")
    @Operation(summary = "Desativar membro", description = "Substituir membresias do usuário ativo por pessoa inativa em todas as unidades do sistema.")
    public ResponseEntity<List<MembershipDto>> deactivateMember(@RequestBody final DeactivationRequest request) {
        return ResponseEntity.ok(deactivationUseCaseService.deactivateAll(request.getUserId()));
    }

}
