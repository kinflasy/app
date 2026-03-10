package br.org.kinflasy.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.dto.CalendarEventDto;
import br.org.kinflasy.services.CalendarEventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/calendar-events")
@Tag(name = "Calendar events")
@AllArgsConstructor
public class CalendarEventController {

    private final CalendarEventService service;

    @GetMapping("unit/{unitId}")
    public ResponseEntity<List<CalendarEventDto>> listByUnitInRange(@PathVariable final UUID unitId,
            @RequestParam final LocalDateTime start, @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(service.listByUnitInRange(unitId, start, end));
    }

    @GetMapping("department/{departmentId}")
    public ResponseEntity<List<CalendarEventDto>> listByDepartmentInRange(@PathVariable final UUID departmentId,
            @RequestParam final LocalDateTime start, @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(service.listByDepartmentInRange(departmentId, start, end));
    }

}
