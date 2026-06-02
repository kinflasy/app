package br.org.kinflasy.apis.calendar.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.calendar.services.scales.ScaleService;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleItemDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleItemRequest;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/calendar-events/scales")
@Tag(name = "Calendar events")
@AllArgsConstructor
public class ScaleController {

    private final ScaleService scaleService;

    @GetMapping("department/{departmentId}")
    @Operation(summary = "Listar escalas do departamento", description = "Listar as escalas associadas aos eventos de um departamento em um intervalo de tempo.")
    public ResponseEntity<List<ScaleDto.DetailingCalendarEvent>> listByDepartmentInRange(
            @PathVariable final UUID departmentId, @RequestParam final LocalDateTime start,
            @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(scaleService.listByDepartmentInRange(departmentId, start, end));
    }

    @GetMapping("unit/{unitId}")
    @Operation(summary = "Listar escalas da unidade", description = "Listar as escalas associadas aos eventos de uma unidade (e suas colaborações) em um intervalo de tempo.")
    public ResponseEntity<List<ScaleDto.DetailingCalendarEvent>> listByUnitInRange(
            @PathVariable final UUID unitId, @RequestParam final LocalDateTime start,
            @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(scaleService.listByUnitInRange(unitId, start, end));
    }

    @GetMapping("{id}/items")
    @Operation(summary = "Listar itens da escala", description = "Listar os itens de uma escala.")
    public ResponseEntity<List<ScaleItemDto>> listItems(@PathVariable final UUID id) {
        return ResponseEntity.ok(scaleService.listItems(id));
    }

    @PostMapping("{id}/items")
    @Operation(summary = "Escalar alguém", description = "Adicionar um item a uma escala.")
    public ResponseEntity<ScaleItemDto> addItem(@PathVariable final UUID id,
            @RequestBody final ScaleItemRequest request) {
        return ResponseEntity.ok(scaleService.addItem(id, request));
    }

    @DeleteMapping("{id}/items")
    @Operation(summary = "Remover alguém da escala", description = "Remover um item de uma escala.")
    public ResponseEntity<Void> removeItem(@PathVariable final UUID id, @RequestBody final ScaleItemRequest request) {
        scaleService.removeItem(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Obter dados da escala", description = "Obter os detalhes de uma escala, incluindo seus itens.")
    public ResponseEntity<ScaleDto> findById(@PathVariable final UUID id) {
        return scaleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar escala", description = "Atualizar os detalhes de uma escala.")
    public ResponseEntity<ScaleDto> update(@PathVariable final UUID id,
            @RequestBody @Valid final ScaleRequest request) {
        return scaleService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remover escala", description = "Remover uma escala.")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        scaleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
