package br.org.kinflasy.api.controllers.core.church;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
import br.org.kinflasy.api.dto.core.church.CreateUnit;
import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.dto.core.church.UpdateChurch;
import br.org.kinflasy.api.services.core.church.ChurchService;
import br.org.kinflasy.api.services.core.church.UnitService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/core/churches")
public class ChurchController {

    private final ChurchService service;
    private final UnitService unitService;

    public ChurchController(@Autowired final ChurchService service, @Autowired final UnitService unitService) {
        this.service = service;
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<List<ChurchDTO>> getAll() {
        return new ResponseEntity<>(service.dto().findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ChurchDTO> create(@RequestBody @Valid final @NonNull CreateChurch form) {
        return new ResponseEntity<>(service.dto().create(form.toChurch()), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ChurchDTO> getById(@PathVariable("id") final @NonNull Integer id) {
        try {
            return new ResponseEntity<>(service.dto().findById(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<ChurchDTO> update(@PathVariable("id") final @NonNull Integer id,
            @RequestBody final @NonNull UpdateChurch form) {
        try {
            final var existingItem = service.findById(id);
            return new ResponseEntity<>(service.dto().update(form.update(existingItem)), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") final @NonNull Integer id) {
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
    public ResponseEntity<List<UnitDTO>> getUnits(@PathVariable("id") final @NonNull Integer id) {
        return new ResponseEntity<>(unitService.dto().nonNull(service.getUnits(id)), HttpStatus.OK);
    }

    @PostMapping("{id}/units")
    public ResponseEntity<UnitDTO> createUnit(@PathVariable("id") final @NonNull Integer id,
            @RequestBody @Valid final @NonNull CreateUnit form) {
        final var createdUnit = service.createUnit(id, form.toUnit());
        return new ResponseEntity<>(unitService.dto().nonNull(createdUnit), HttpStatus.CREATED);
    }

}
