package br.org.kinflasy.api.controllers.core.church;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.org.kinflasy.api.dto.core.church.ChurchDTO;
import br.org.kinflasy.api.dto.core.church.CreateChurch;
import br.org.kinflasy.api.dto.core.church.CreateStarterChurch;
import br.org.kinflasy.api.dto.core.church.CreateUnit;
import br.org.kinflasy.api.dto.core.church.StarterChurchDTO;
import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.dto.core.church.UpdateChurch;
import br.org.kinflasy.api.services.core.church.ChurchService;
import br.org.kinflasy.api.services.core.church.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/core/churches")
@Tag(name = "Church")
public class ChurchController {

    private final ChurchService service;
    private final UnitService unitService;

    public ChurchController(@Autowired final ChurchService service, @Autowired final UnitService unitService) {
        this.service = service;
        this.unitService = unitService;
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todas as igrejas cadastradas.")
    public ResponseEntity<List<ChurchDTO>> getAll() {
        return new ResponseEntity<>(service.dto().findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar", description = "Cadastrar uma igreja.")
    public ResponseEntity<ChurchDTO> create(@RequestBody @Valid final CreateChurch form) {
        return new ResponseEntity<>(service.dto().create(form.toChurch()), HttpStatus.CREATED);
    }

    @PostMapping("starter")
    @Transactional
    @Operation(summary = "Cadastrar com itens principais", description = "Cadastrar uma igreja com unidade sede e departamentos principais.")
    public ResponseEntity<StarterChurchDTO> createStarter(@RequestBody @Valid final CreateStarterChurch form) {
        return new ResponseEntity<>(StarterChurchDTO.ofNonNull(service.createStarter(form)), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma igreja pelo ID.")
    public ResponseEntity<ChurchDTO> getById(@PathVariable("id") final Integer id) {
        try {
            return new ResponseEntity<>(service.dto().findById(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de uma igreja.")
    public ResponseEntity<ChurchDTO> update(@PathVariable("id") final Integer id,
            @RequestBody final UpdateChurch form) {
        try {
            final var existingItem = service.findById(id);
            return new ResponseEntity<>(service.dto().update(form.update(existingItem)), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar uma igreja, removendo-a do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") final Integer id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("{id}/units")
    @Operation(summary = "Listar unidades", description = "Listar as unidades de uma igreja.")
    public ResponseEntity<List<UnitDTO>> getUnits(@PathVariable("id") final Integer id) {
        return new ResponseEntity<>(unitService.dto().nonNull(service.getUnits(id)), HttpStatus.OK);
    }

    @PostMapping("{id}/units")
    @Operation(summary = "Cadastrar unidade", description = "Cadastrar uma nova unidade em uma igreja.")
    public ResponseEntity<UnitDTO> createUnit(@PathVariable("id") final Integer id,
            @RequestBody @Valid final CreateUnit form) {
        final var createdUnit = service.createUnit(id, form.toUnit());
        return new ResponseEntity<>(unitService.dto().nonNull(createdUnit), HttpStatus.CREATED);
    }

}
