package br.org.kinflasy.apis.calendar.services.scales;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.calendar.entities.scales.CollaboratorScale;
import br.org.kinflasy.apis.calendar.entities.scales.OwnerScale;
import br.org.kinflasy.apis.calendar.entities.scales.Scale;
import br.org.kinflasy.apis.calendar.repositories.scales.ScaleRepository;
import br.org.kinflasy.libs.calendar.dto.scales.CollaboratorScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.OwnerScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleDto;
import br.org.kinflasy.libs.calendar.dto.scales.ScaleRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScaleService {

    private final ScaleRepository repository;

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
