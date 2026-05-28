package br.org.kinflasy.apis.calendar.services.scales;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.calendar.entities.scales.CollaboratorScale;
import br.org.kinflasy.apis.calendar.entities.scales.OwnerScale;
import br.org.kinflasy.apis.calendar.entities.scales.Scale;
import br.org.kinflasy.apis.calendar.entities.scales.ScaleItem;
import br.org.kinflasy.apis.calendar.repositories.scales.ScaleItemRepository;
import br.org.kinflasy.apis.calendar.repositories.scales.ScaleRepository;
import br.org.kinflasy.libs.calendar.dto.scales.CollaboratorScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.OwnerScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleItemDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleItemRequest;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScaleService {

    private final ModelMapper mapper;

    private final ScaleRepository repository;
    private final ScaleItemRepository itemRepository;

    private final ApplicationEventPublisher publisher;

    public Optional<ScaleDto> findById(final UUID id) {
        return repository.findById(id)
                .map(this::toDto);
    }

    public Optional<ScaleDto> update(final UUID id, final ScaleRequest request) {
        return repository.findById(id)
                .map(scale -> {
                    // Guardar estado original
                    final var original = toDto(scale);

                    // Atualizar e salvar
                    scale.setLineupId(request.getLineupId());
                    final var modified = toDto(repository.save(scale));

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Updated<>(original, modified));

                    return modified;
                });
    }

    public void delete(final UUID id) {
        repository.findById(id)
                .ifPresent(scale -> {
                    // Guardar estado original
                    final var original = toDto(scale);

                    // Deletar
                    repository.delete(scale);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(original));
                });
    }

    public List<ScaleItemDto> listItems(final UUID scaleId) {
        return itemRepository.findByScaleId(scaleId).stream()
                .map(item -> mapper.map(item, ScaleItemDto.class))
                .toList();
    }

    public ScaleItemDto addItem(final UUID scaleId, final ScaleItemRequest request) {
        return itemRepository.findByScaleIdAndPersonIdAndRoleId(scaleId, request.getPersonId(), request.getRoleId())
                .map(entity -> mapper.map(entity, ScaleItemDto.class))
                .orElseGet(() -> {
                    // Construir entidade
                    final var entity = new ScaleItem();
                    entity.setScaleId(scaleId);
                    entity.setPersonId(request.getPersonId());
                    entity.setRoleId(request.getRoleId());

                    // Salvar e converter para DTO
                    final var saved = itemRepository.save(entity);
                    final var dto = mapper.map(saved, ScaleItemDto.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Created<>(dto));

                    return dto;
                });
    }

    public void removeItem(final UUID itemId) {
        itemRepository.findById(itemId)
                .ifPresent(item -> {
                    // Guardar estado original
                    final var original = mapper.map(item, ScaleItemDto.class);

                    // Deletar
                    itemRepository.delete(item);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(original));
                });
    }

    public void removeItem(final UUID scaleId, final ScaleItemRequest request) {
        itemRepository.findByScaleIdAndPersonIdAndRoleId(scaleId, request.getPersonId(), request.getRoleId())
                .ifPresent(item -> {
                    // Guardar estado original
                    final var original = mapper.map(item, ScaleItemDto.class);

                    // Deletar
                    itemRepository.delete(item);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(original));
                });
    }

    private ScaleDto toDto(final Scale scale) {
        return switch (scale) {
            case OwnerScale ownerScale -> new OwnerScaleDto()
                    .setCalendarEventId(ownerScale.getCalendarEventId())
                    .setId(ownerScale.getId())
                    .setLineupId(ownerScale.getLineupId());
            case CollaboratorScale collaboratorScale -> new CollaboratorScaleDto()
                    .setCollaborationId(collaboratorScale.getCollaborationId())
                    .setId(collaboratorScale.getId())
                    .setLineupId(collaboratorScale.getLineupId());
            default ->
                throw new IllegalStateException("Tipo desconhecido de escala: " + scale.getClass().getName());
        };
    }

}
