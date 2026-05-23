package br.org.kinflasy.apis.churches.controllers.lineups;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.churches.services.lineups.LineupService;
import br.org.kinflasy.libs.churches.dto.lineups.LineupDto;
import br.org.kinflasy.libs.churches.dto.lineups.LineupRequest;
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
    @Operation(summary = "Detalhar", description = "Detalhar uma formação pelo ID.")
    public ResponseEntity<LineupDto> findById(@PathVariable final UUID id) {
        return service.findById(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("{id}/with-items")
    @Operation(summary = "Detalhar com itens", description = "Detalhar uma formação pelo ID, incluindo seus itens (papel + descrição).")
    public ResponseEntity<LineupDto.WithItems> findByIdWithItems(@PathVariable final UUID id) {
        return service.findById(id)
                .map(dto -> {
                    final var withItems = new LineupDto.WithItems();
                    withItems
                            .setItems(service.listItems(id))
                            .setId(dto.getId())
                            .setName(dto.getName());

                    return new ResponseEntity<>(withItems, HttpStatus.OK);
                })
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

    @GetMapping("{id}/items")
    @Operation(summary = "Listar itens", description = "Listar os itens (papel + descrição) de uma formação.")
    public ResponseEntity<List<LineupDto.Item>> listItems(@PathVariable final UUID id) {
        return service.findById(id)
                .map(lineup -> new ResponseEntity<>(service.listItems(id), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/items")
    @Operation(summary = "Adicionar item", description = "Adicionar um novo item (papel + descrição) a uma formação.")
    public ResponseEntity<LineupDto.Item> addItem(@PathVariable final UUID id,
            @RequestBody @Valid final LineupRequest.Item request) {
        return service.addItem(id, request)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("items/{id}")
    @Operation(summary = "Editar item", description = "Editar os dados de um item de formação.")
    public ResponseEntity<LineupDto.Item> updateItem(@PathVariable final UUID id,
            @RequestBody @Valid final LineupRequest.UpdateItem request) {
        return service.updateItem(id, request)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("items/{id}")
    @Operation(summary = "Excluir item", description = "Remover um item de formação do sistema.")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable final UUID id) {
        try {
            service.deleteItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (final EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
