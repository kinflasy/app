package br.org.kinflasy.apis.churches.services.lineups;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.clients.RoleClient;
import br.org.kinflasy.apis.churches.entities.lineups.LineupItem;
import br.org.kinflasy.apis.churches.repositories.lineups.LineupItemRepository;
import br.org.kinflasy.libs.churches.dto.lineups.LineupDto;
import br.org.kinflasy.libs.churches.dto.lineups.LineupRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LineupItemService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final LineupItemRepository itemRepository;

    private final RoleClient roleClient;

    @PreAuthorize("@fga.check('lineup', #lineupId, 'can_view', 'user', principal.id)")
    public List<LineupDto.Item.DetailingRole> listByLineupId(final UUID lineupId) {
        return itemRepository.findByLineupId(lineupId).stream()
                .map(item -> mapper.map(item, LineupDto.Item.DetailingRole.class)
                        .setRole(roleClient.findById(item.getRoleId())))
                .toList();
    }

    @PreAuthorize("@fga.check('lineup', #lineupId, 'can_edit', 'user', principal.id)")
    public LineupDto.Item create(final UUID lineupId, final LineupRequest.Item request) {
        // Criar e salvar
        final var entity = mapper.map(request, LineupItem.class);
        entity.setId(null);
        entity.setLineupId(lineupId);

        final var saved = itemRepository.save(entity);

        final var dto = mapper.map(saved, LineupDto.Item.class);

        // Publicar evento
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

    @PreAuthorize("@fga.check('lineup_item', #id, 'can_edit', 'user', principal.id)")
    public Optional<LineupDto.Item> update(final UUID id, final LineupRequest.UpdateItem request) {
        return itemRepository.findById(id)
                .map(item -> {
                    final var original = mapper.map(item, LineupDto.Item.class);

                    // Atualizar e salvar
                    mapper.map(request, item);
                    final var saved = itemRepository.save(item);

                    final var modified = mapper.map(saved, LineupDto.Item.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Updated<>(original, modified));

                    return modified;
                });
    }

    @PreAuthorize("@fga.check('lineup_item', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        itemRepository.findById(id)
                .ifPresent(item -> {
                    final var dto = mapper.map(item, LineupDto.Item.class);

                    // Deletar
                    itemRepository.delete(item);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(dto));
                });
    }

    @PreAuthorize("@fga.check('lineup_item', #id, 'can_edit', 'user', principal.id)")
    public void deleteAllByLineupId(final UUID lineUpId) {
        itemRepository.findByLineupId(lineUpId)
                .forEach(item -> {
                    final var dto = mapper.map(item, LineupDto.Item.class);

                    // Deletar
                    itemRepository.delete(item);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(dto));
                });
    }

}
