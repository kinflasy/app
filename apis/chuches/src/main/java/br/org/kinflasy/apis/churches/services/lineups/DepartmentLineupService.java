package br.org.kinflasy.apis.churches.services.lineups;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.entities.lineups.DepartmentLineup;
import br.org.kinflasy.apis.churches.repositories.lineups.DepartmentLineupRepository;
import br.org.kinflasy.libs.churches.dto.lineups.DepartmentLineupDto;
import br.org.kinflasy.libs.churches.dto.lineups.LineupRequest;
import br.org.kinflasy.libs.lib_utils.EntityEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentLineupService {

    private final ModelMapper mapper;
    private final ApplicationEventPublisher publisher;

    private final DepartmentLineupRepository repository;

    public List<DepartmentLineupDto> listAllByDepartmentId(final UUID departmentId) {
        return repository.findAllByDepartmentId(departmentId).stream()
                .map(entity -> mapper.map(entity, DepartmentLineupDto.class))
                .toList();
    }

    public DepartmentLineupDto create(final UUID departmentId, final LineupRequest request) {
        // Construir entidade
        final var entity = mapper.map(request, DepartmentLineup.class);
        entity.setDepartmentId(departmentId);

        // Salvar
        final var saved = repository.save(entity);

        // Publicar evento
        final var dto = mapper.map(saved, DepartmentLineupDto.class);
        publisher.publishEvent(new EntityEvent.Created<>(dto));

        return dto;
    }

}
