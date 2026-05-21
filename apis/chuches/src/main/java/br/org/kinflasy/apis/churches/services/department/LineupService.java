package br.org.kinflasy.apis.churches.services.department;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.entities.department.DepartmentLineup;
import br.org.kinflasy.apis.churches.entities.department.Lineup;
import br.org.kinflasy.apis.churches.entities.department.UnitLineup;
import br.org.kinflasy.apis.churches.repositories.department.LineupRepository;
import br.org.kinflasy.libs.churches.dto.departments.LineupDto;
import br.org.kinflasy.libs.churches.dto.departments.LineupRequest;
import br.org.kinflasy.libs.churches.dto.departments.UnitLineupDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LineupService {

    private ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private LineupRepository repository;

    public Optional<LineupDto> findById(final UUID id) {
        return repository.findById(id)
                .map(this::toDto);
    }

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
