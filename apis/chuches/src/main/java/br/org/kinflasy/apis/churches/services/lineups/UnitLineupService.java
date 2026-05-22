package br.org.kinflasy.apis.churches.services.lineups;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.entities.lineups.UnitLineup;
import br.org.kinflasy.apis.churches.repositories.lineups.UnitLineupRepository;
import br.org.kinflasy.libs.churches.dto.lineups.LineupRequest;
import br.org.kinflasy.libs.churches.dto.lineups.UnitLineupDto;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnitLineupService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final UnitLineupRepository repository;

    public List<UnitLineupDto> listAllByUnitId(final UUID unitId) {
        return repository.findAllByUnitId(unitId).stream()
                .map(entity -> mapper.map(entity, UnitLineupDto.class))
                .toList();
    }

    public UnitLineupDto create(final UUID unitId, final LineupRequest request) {
        // Construir entidade
        final var entity = mapper.map(request, UnitLineup.class);
        entity.setUnitId(unitId);

        // Salvar
        final var saved = repository.save(entity);

        // Publicar evento
        final var dto = mapper.map(saved, UnitLineupDto.class);
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

}
