package br.org.kinflasy.api.controllers.core.church.department;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.api.dto.core.church.department.DepartmentDTO;
import br.org.kinflasy.api.dto.core.church.department.UpdateDepartment;
import br.org.kinflasy.api.services.core.church.department.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("v1/core/church/unit/departments")
@Tag(name = "Department")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(@Autowired final DepartmentService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar um departamento pelo ID.")
    public ResponseEntity<DepartmentDTO> getById(@PathVariable("id") final UUID id) {
        try {
            return new ResponseEntity<>(service.dto().findById(id), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Editar os dados de um departamento.")
    public ResponseEntity<DepartmentDTO> update(@PathVariable("id") final UUID id,
            @RequestBody final UpdateDepartment form) {
        try {
            final var existingItem = service.findById(id);
            return new ResponseEntity<>(service.dto().update(form.update(existingItem)), HttpStatus.OK);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @Operation(summary = "Excluir", description = "Descadastrar um departamento, removendo-o do sistema.")
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

}
