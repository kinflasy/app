package br.org.kinflasy.apis.churches.services.lineups;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.entities.lineups.DepartmentLineup;
import br.org.kinflasy.apis.churches.entities.lineups.Lineup;
import br.org.kinflasy.apis.churches.entities.lineups.UnitLineup;
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

    private LineupDto toDto(Lineup lineup) {
        return switch (lineup) {
            case final UnitLineup unitLineup -> mapper.map(unitLineup, UnitLineupDto.class);
            case final DepartmentLineup departmentLineup -> mapper.map(departmentLineup, LineupDto.class);
            default -> throw new IllegalArgumentException(
                    "Tipo de formação desconhecido: " + lineup.getClass().getName());
        };
    }

}
