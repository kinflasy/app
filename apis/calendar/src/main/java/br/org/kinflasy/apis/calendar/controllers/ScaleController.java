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
    public ResponseEntity<List<ScaleDto.DetailingCalendarEvent>> listByDepartmentInRange(
            @PathVariable final UUID departmentId, @RequestParam final LocalDateTime start,
            @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(scaleService.listByDepartmentInRange(departmentId, start, end));
    }

    @GetMapping("unit/{unitId}")
    public ResponseEntity<List<ScaleDto.DetailingCalendarEvent>> listByUnitInRange(
            @PathVariable final UUID unitId, @RequestParam final LocalDateTime start,
            @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(scaleService.listByUnitInRange(unitId, start, end));
    }

    @GetMapping("{id}/items")
    public ResponseEntity<List<ScaleItemDto>> listItems(@PathVariable final UUID id) {
        return ResponseEntity.ok(scaleService.listItems(id));
    }

    @PostMapping("{id}/items")
    public ResponseEntity<ScaleItemDto> addItem(@PathVariable final UUID id,
            @RequestBody final ScaleItemRequest request) {
        return ResponseEntity.ok(scaleService.addItem(id, request));
    }

    @DeleteMapping("{id}/items")
    public ResponseEntity<Void> removeItem(@PathVariable final UUID id, @RequestBody final ScaleItemRequest request) {
        scaleService.removeItem(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ScaleDto> findById(@PathVariable final UUID id) {
        return scaleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<ScaleDto> update(@PathVariable final UUID id,
            @RequestBody @Valid final ScaleRequest request) {
        return scaleService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        scaleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
