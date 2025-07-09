package br.org.kinflasy.api.apis.churches.controllers;

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

import br.org.kinflasy.api.apis.churches.services.UnitService;
import br.org.kinflasy.api.apis.churches.services.department.DepartmentService;
import br.org.kinflasy.api.libs.churches.dto.UnitDto;
import br.org.kinflasy.api.libs.churches.dto.UnitRequest;
import br.org.kinflasy.api.libs.churches.dto.departments.DepartmentDto;
import br.org.kinflasy.api.libs.churches.dto.departments.DepartmentRequest;
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
    private final DepartmentService departmentService;

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma unidade pelo ID.")
    public ResponseEntity<UnitDto> getById(@PathVariable("id") final UUID id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de uma unidade.")
    public ResponseEntity<UnitDto> update(@PathVariable("id") final UUID id,
            @RequestBody final UnitRequest.Update form) {
        try {
            return new ResponseEntity<>(service.update(form), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar uma unidade, removendo-a do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") final UUID id) {
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
    @Operation(summary = "Listar departamentos", description = "Listar os departamentos de uma unidade.")
    public ResponseEntity<List<DepartmentDto>> getDepartments(@PathVariable("id") final UUID id) {
        return new ResponseEntity<>(service.getDepartments(id), HttpStatus.OK);
    }

    @PostMapping("{id}/departments")
    @Operation(summary = "Cadastrar departamento", description = "Cadastrar um novo departamento em uma unidade.")
    public ResponseEntity<DepartmentDto> createDepartment(@PathVariable("id") final UUID id,
            @RequestBody @Valid final DepartmentRequest.Create form) {
        return new ResponseEntity<>(departmentService.create(form), HttpStatus.CREATED);
    }

}
