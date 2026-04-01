package br.org.kinflasy.controllers;

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

import br.org.kinflasy.dto.CalendarEventDto;
import br.org.kinflasy.dto.CalendarEventRequest;
import br.org.kinflasy.dto.DepartmentCalendarEventDto;
import br.org.kinflasy.dto.UnitCalendarEventDto;
import br.org.kinflasy.services.CalendarEventService;
import br.org.kinflasy.services.DepartmentCalendarEventService;
import br.org.kinflasy.services.UnitCalendarEventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/calendar-events")
@Tag(name = "Calendar events")
@AllArgsConstructor
public class CalendarEventController {

    private final CalendarEventService service;
    private final UnitCalendarEventService unitService;
    private final DepartmentCalendarEventService departmentService;

    @GetMapping("unit/{unitId}")
    public ResponseEntity<List<UnitCalendarEventDto>> listByUnitInRange(@PathVariable final UUID unitId,
            @RequestParam final LocalDateTime start, @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(unitService.listInRange(unitId, start, end));
    }

    @GetMapping("department/{departmentId}")
    public ResponseEntity<List<DepartmentCalendarEventDto>> listByDepartmentInRange(
            @PathVariable final UUID departmentId, @RequestParam final LocalDateTime start,
            @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(departmentService.listInRange(departmentId, start, end));
    }

    @PostMapping("unit/{unitId}")
    @Transactional
    public ResponseEntity<UnitCalendarEventDto> createWithUnit(@PathVariable final UUID unitId,
            @RequestBody final CalendarEventRequest request) {
        return ResponseEntity.ok(unitService.create(unitId, request));
    }

    @PostMapping("department/{departmentId}")
    @Transactional
    public ResponseEntity<DepartmentCalendarEventDto> createWithDepartment(@PathVariable final UUID departmentId,
            @RequestBody final CalendarEventRequest request) {
        return ResponseEntity.ok(departmentService.create(departmentId, request));
    }

    @GetMapping("{id}")
    public ResponseEntity<CalendarEventDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<CalendarEventDto> update(@PathVariable final UUID id,
            @RequestBody final CalendarEventDto request) {
        try {
            return ResponseEntity.ok(service.update(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}/delete")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
