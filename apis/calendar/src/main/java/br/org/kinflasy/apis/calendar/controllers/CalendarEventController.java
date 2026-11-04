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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.kinflasy.apis.calendar.services.CalendarEventService;
import br.org.kinflasy.apis.calendar.services.DepartmentCalendarEventService;
import br.org.kinflasy.apis.calendar.services.UnitCalendarEventService;
import br.org.kinflasy.libs.calendar.dto.CalendarEventDto;
import br.org.kinflasy.libs.calendar.dto.CalendarEventRequest;
import br.org.kinflasy.libs.calendar.dto.DepartmentCalendarEventDto;
import br.org.kinflasy.libs.calendar.dto.EventCollaborationDto;
import br.org.kinflasy.libs.calendar.dto.UnitCalendarEventDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleRequest;
import br.org.kinflasy.libs.churches.dto.departments.DepartmentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/calendar-events")
@Tag(name = "Calendar events")
@AllArgsConstructor
public class CalendarEventController {

    private final CalendarEventService service;
    private final UnitCalendarEventService unitService;
    private final DepartmentCalendarEventService departmentService;

    @GetMapping("visible")
    @Operation(summary = "Listar eventos visíveis", description = "Listar eventos que o usuário tem permissão para visualizar em um intervalo de tempo.")
    public ResponseEntity<List<CalendarEventDto>> listVisibleInRange(@RequestParam final LocalDateTime start,
            @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(service.listVisibleInRange2(start, end));
    }

    @GetMapping("unit/{unitId}")
    @Operation(summary = "Listar eventos da unidade", description = "Listar eventos de uma unidade em um intervalo de tempo.")
    public ResponseEntity<List<UnitCalendarEventDto>> listByUnitInRange(@PathVariable final UUID unitId,
            @RequestParam final LocalDateTime start, @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(unitService.listInRange(unitId, start, end));
    }

    @GetMapping("department/{departmentId}")
    @Operation(summary = "Listar eventos de um departamento", description = "Listar eventos de um departamento em um intervalo de tempo.")
    public ResponseEntity<List<DepartmentCalendarEventDto>> listByDepartmentInRange(
            @PathVariable final UUID departmentId, @RequestParam final LocalDateTime start,
            @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(departmentService.listInRange(departmentId, start, end));
    }

    @GetMapping("department/{departmentId}/collabs")
    @Operation(summary = "Listar os eventos com os quais o departamento colabora", description = "Listar colaborações de um departamento em um intervalo de tempo.")
    public ResponseEntity<List<CalendarEventDto>> listCollabsByDepartmentInRange(@PathVariable final UUID departmentId,
            @RequestParam final LocalDateTime start, @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(service.listCollaborationsInRange(departmentId, start, end));
    }

    @GetMapping("department/{departmentId}/with-collabs")
    @Operation(summary = "Listar eventos do departamento junto com suas colaborações", description = "Listar eventos de um departamento em um intervalo de tempo, incluindo colaborações.")
    public ResponseEntity<List<CalendarEventDto>> listByDepartmentInRangeWithCollabs(
            @PathVariable final UUID departmentId, @RequestParam final LocalDateTime start,
            @RequestParam final LocalDateTime end) {
        return ResponseEntity.ok(departmentService.listInRangeWithCollabs(departmentId, start, end));
    }

    @PostMapping("unit/{unitId}")
    @Operation(summary = "Criar evento para a unidade", description = "Criar um novo evento para uma unidade.")
    public ResponseEntity<UnitCalendarEventDto> createWithUnit(@PathVariable final UUID unitId,
            @RequestBody final CalendarEventRequest request) {
        return ResponseEntity.ok(unitService.create(unitId, request));
    }

    @PostMapping("department/{departmentId}")
    @Operation(summary = "Criar evento para o departamento", description = "Criar um novo evento para um departamento.")
    public ResponseEntity<DepartmentCalendarEventDto> createWithDepartment(@PathVariable final UUID departmentId,
            @RequestBody final CalendarEventRequest request) {
        return ResponseEntity.ok(departmentService.create(departmentId, request));
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar evento por ID", description = "Buscar um evento específico pelo seu ID.")
    public ResponseEntity<CalendarEventDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar evento", description = "Atualizar informações de um evento existente.")
    public ResponseEntity<CalendarEventDto> update(@PathVariable final UUID id,
            @RequestBody final CalendarEventRequest request) {
        try {
            return ResponseEntity.ok(service.update(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "{id}/card-image", consumes = "multipart/form-data")
    @Operation(summary = "Atualizar card", description = "Atualizar card do evento.")
    public ResponseEntity<CalendarEventDto> updateCardImage(@PathVariable final UUID id,
            @RequestPart final MultipartFile file) {
        return service.updateCardImage(id, file)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}/card-image")
    @Operation(summary = "Deletar card", description = "Deletar card do evento.")
    public ResponseEntity<CalendarEventDto> deleteCardImage(@PathVariable final UUID id) {
        return service.deleteCardImage(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}/delete")
    @Operation(summary = "Deletar evento", description = "Deletar um evento existente.")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/collaborators")
    @Operation(summary = "Listar colaboradores", description = "Listar os departamentos que colaboram com o evento.")
    public ResponseEntity<List<DepartmentDto>> listCollaborators(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.listCollaborators(id));
    }

    @PostMapping("{id}/collaborators/{departmentId}")
    @Operation(summary = "Adicionar departamento colaborador", description = "Adicionar um departamento como colaborador do evento.")
    public ResponseEntity<EventCollaborationDto> addCollaboratingDepartment(@PathVariable final UUID id,
            @PathVariable final UUID departmentId) {
        return service.addCollaborator(id, departmentId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("{id}/collaborators/{departmentId}")
    @Operation(summary = "Remover departamento colaborador", description = "Remover um departamento como colaborador do evento.")
    public ResponseEntity<Void> removeCollaboratingDepartment(@PathVariable final UUID id,
            @PathVariable final UUID departmentId) {
        service.removeCollaborator(id, departmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/scales")
    @Operation(summary = "Listar escalas", description = "Listar as escalas associadas ao evento.")
    public ResponseEntity<List<ScaleDto>> listScales(@PathVariable final UUID id) {
        return ResponseEntity.ok(service.listScales(id));
    }

    @PostMapping("{id}/scales")
    @Operation(summary = "Criar escala como dono do evento", description = "Criar uma nova escala para o evento como dono (unidade ou departamento).")
    public ResponseEntity<ScaleDto> createOwnerScale(@PathVariable final UUID id,
            @RequestBody final ScaleRequest request) {
        return ResponseEntity.ok(service.createOwnerScale(id, request));
    }

    @PostMapping("{id}/collaborators/{departmentId}/scales")
    @Operation(summary = "Criar escala como colaborador", description = "Criar uma nova escala para o evento como colaborador.")
    public ResponseEntity<ScaleDto> createCollaboratorScale(@PathVariable final UUID id,
            @PathVariable final UUID departmentId, @RequestBody final ScaleRequest request) {
        return service.createCollaboratorScale(id, departmentId, request)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

}
