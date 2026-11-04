package br.org.kinflasy.apis.calendar.services.scales;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.calendar.entities.scales.ScaleItem;
import br.org.kinflasy.apis.calendar.repositories.scales.ScaleItemRepository;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleItemDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleItemRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScaleItemService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;
    private final ScaleItemRepository repository;

    @PreAuthorize("@fga.check('scale', #scaleId, 'can_edit', 'user', principal.id)")
    public ScaleItemDto create(final UUID scaleId, final ScaleItemRequest request) {
        // Verificar se o item já existe e retorná-lo
        return repository.findByScaleIdAndPersonIdAndRoleId(scaleId, request.getPersonId(), request.getRoleId())
                .map(this::toDto)

                // Se não existir, criar um novo item
                .orElseGet(() -> {
                    // Construir entidade
                    final var entity = new ScaleItem();
                    entity.setScaleId(scaleId);
                    entity.setPersonId(request.getPersonId());
                    entity.setRoleId(request.getRoleId());

                    // Salvar e converter para DTO
                    final var saved = repository.save(entity);
                    final var dto = toDto(saved);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Created<>(dto));

                    return dto;
                });
    }

    @PreAuthorize("@fga.check('scale', #scaleId, 'can_view', 'user', principal.id)")
    public List<ScaleItemDto> listByScaleId(final UUID scaleId) {
        return repository.findByScaleId(scaleId).stream()
                .map(this::toDto)
                .toList();
    }

    @PreAuthorize("@fga.check('scale', #scaleId, 'can_edit', 'user', principal.id)")
    public void delete(final UUID scaleId, final ScaleItemRequest request) {
        repository.findByScaleIdAndPersonIdAndRoleId(scaleId, request.getPersonId(), request.getRoleId())
                .ifPresent(this::deleteAndPublish);
    }

    @PreAuthorize("@fga.check('scale', #scaleId, 'can_edit', 'user', principal.id)")
    public void deleteAllByScaleId(final UUID scaleId) {
        deleteAndPublish(repository.findByScaleId(scaleId));
    }

    private void deleteAndPublish(final ScaleItem item) {
        deleteAndPublish(List.of(item));
    }

    private void deleteAndPublish(final Collection<ScaleItem> items) {
        // Guardar estado original
        final var originals = items.stream()
                .map(this::toDto)
                .toList();

        // Deletar
        repository.deleteAll(items);

        // Publicar evento
        originals.forEach(original -> publisher.publishEvent(new EntityEvent.Deleted<>(original)));
    }

    private ScaleItemDto toDto(final ScaleItem entity) {
        return mapper.map(entity, ScaleItemDto.class);
    }

}
