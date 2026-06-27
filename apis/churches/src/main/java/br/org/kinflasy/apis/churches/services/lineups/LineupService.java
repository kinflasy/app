package br.org.kinflasy.apis.churches.services.lineups;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.clients.RoleClient;
import br.org.kinflasy.apis.churches.entities.lineups.DepartmentLineup;
import br.org.kinflasy.apis.churches.entities.lineups.Lineup;
import br.org.kinflasy.apis.churches.entities.lineups.LineupItem;
import br.org.kinflasy.apis.churches.entities.lineups.UnitLineup;
import br.org.kinflasy.apis.churches.repositories.lineups.LineupItemRepository;
import br.org.kinflasy.apis.churches.repositories.lineups.LineupRepository;
import br.org.kinflasy.libs.churches.dto.lineups.LineupDto;
import br.org.kinflasy.libs.churches.dto.lineups.LineupRequest;
import br.org.kinflasy.libs.churches.dto.lineups.UnitLineupDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LineupService {

    private ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private LineupRepository repository;
    private LineupItemRepository itemRepository;

    private RoleClient roleClient;

    @PreAuthorize("@fga.check('lineup', #id, 'can_view', 'user', principal.id)")
    public Optional<LineupDto> findById(final UUID id) {
        return repository.findById(id)
                .map(this::toDto);
    }

    @PreAuthorize("@fga.check('lineup', #id, 'can_edit', 'user', principal.id)")
    public Optional<LineupDto> update(final UUID id, final LineupRequest request) {
        return repository.findById(id)
                .map(lineup -> {
                    final var original = toDto(lineup);

                    // Atualizar e salvar
                    mapper.map(request, lineup);
                    final var modified = toDto(lineup);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Updated<>(original, modified));

                    return modified;
                });
    }

    @PreAuthorize("@fga.check('lineup', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        repository.findById(id)
                .ifPresent(lineup -> {
                    final var dto = toDto(lineup);
                    repository.delete(lineup);
                    publisher.publishEvent(new EntityEvent.Deleted<>(dto));
                });
    }

    @PreAuthorize("@fga.check('lineup', #id, 'can_view', 'user', principal.id)")
    public List<LineupDto.Item.DetailingRole> listItems(final UUID id) {
        return itemRepository.findByLineupId(id).stream()
                .map(item -> mapper.map(item, LineupDto.Item.DetailingRole.class)
                        .setRole(roleClient.findById(item.getRoleId())))
                .toList();
    }

    @PreAuthorize("@fga.check('lineup', #id, 'can_edit', 'user', principal.id)")
    public Optional<LineupDto.Item> addItem(final UUID id, final LineupRequest.Item request) {
        return repository.findById(id)
                .map(lineup -> {
                    // Criar e salvar
                    final var entity = mapper.map(request, LineupItem.class);
                    entity.setId(null);
                    entity.setLineupId(id);

                    final var saved = itemRepository.save(entity);

                    final var dto = mapper.map(saved, LineupDto.Item.class);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Created<>(dto));

                    return dto;
                });
    }

    @PreAuthorize("@fga.check('lineup_item', #id, 'can_edit', 'user', principal.id)")
    public Optional<LineupDto.Item> updateItem(final UUID id, final LineupRequest.UpdateItem request) {
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
    public void deleteItem(final UUID id) {
        itemRepository.findById(id)
                .ifPresent(item -> {
                    final var dto = mapper.map(item, LineupDto.Item.class);

                    // Deletar
                    itemRepository.delete(item);

                    // Publicar evento
                    publisher.publishEvent(new EntityEvent.Deleted<>(dto));
                });
    }

    private LineupDto toDto(Lineup lineup) {
        return switch (lineup) {
            case final UnitLineup unitLineup -> mapper.map(unitLineup, UnitLineupDto.class);
            case final DepartmentLineup departmentLineup -> mapper.map(departmentLineup, LineupDto.class);
            default -> throw new IllegalArgumentException(
                    "Tipo de formação desconhecido: " + lineup.getClass().getName());
        };
    }

}
