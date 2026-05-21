package br.org.kinflasy.apis.churches.controllers.department;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.churches.services.department.LineupService;
import br.org.kinflasy.libs.churches.dto.departments.LineupDto;
import br.org.kinflasy.libs.churches.dto.departments.LineupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/church/unit/lineups")
@Tag(name = "Lineup")
@AllArgsConstructor
public class LineupController {

    private final LineupService service;

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Buscar uma formação pelo ID.")
    public ResponseEntity<LineupDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @Operation(summary = "Editar", description = "Editar os dados de uma formação.")
    public ResponseEntity<LineupDto> update(@PathVariable final UUID id,
            @RequestBody @Valid final LineupRequest request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir", description = "Descadastrar uma formação, removendo-a do sistema.")
    public ResponseEntity<HttpStatus> delete(@PathVariable final UUID id) {
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
