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

import br.org.kinflasy.api.dto.core.church.UnitDTO;
import br.org.kinflasy.api.dto.core.church.UpdateUnit;
import br.org.kinflasy.api.dto.core.church.department.CreateDepartment;
import br.org.kinflasy.api.dto.core.church.department.DepartmentDTO;
import br.org.kinflasy.api.services.core.church.UnitService;
import br.org.kinflasy.api.services.core.church.department.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/core/church/units")
public class UnitController {

    private final UnitService service;
    private final DepartmentService departmentService;

    public UnitController(@Autowired final UnitService service, @Autowired final DepartmentService departmentService) {
        this.service = service;
        this.departmentService = departmentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UnitDTO> getById(@PathVariable("id") final @NonNull Integer id) {
        try {
            return new ResponseEntity<>(service.dto().findById(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<UnitDTO> update(@PathVariable("id") final @NonNull Integer id,
            @RequestBody final @NonNull UpdateUnit form) {
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

    @GetMapping("{id}/departments")
    public ResponseEntity<List<DepartmentDTO>> getDepartments(@PathVariable("id") final @NonNull Integer id) {
        return new ResponseEntity<>(departmentService.dto().nonNull(service.getDepartments(id)), HttpStatus.OK);
    }

    @PostMapping("{id}/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@PathVariable("id") final @NonNull Integer id,
            @RequestBody @Valid final @NonNull CreateDepartment form) {
        final var createdDepartment = service.createDepartment(id, form.toDepartment());
        return new ResponseEntity<>(departmentService.dto().nonNull(createdDepartment), HttpStatus.CREATED);
    }

}
