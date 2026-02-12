package br.org.kinflasy.apis.churches.services.department;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.churches.repositories.MembershipRepository;
import br.org.kinflasy.libs.churches.dto.MembershipDto;
import br.org.kinflasy.libs.churches.dto.MembershipRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class MembershipService {

    private static final String NOT_FOUND_MESSAGE = "Relação de membresia não encontrada";

    private final ModelMapper mapper;
    private final MembershipRepository repository;

    @PreAuthorize("#personId.equals(principal.id)")
    public List<MembershipDto> findByPersonId(final UUID personId) {
        return repository.findByPersonId(personId).stream()
                .map(entity -> mapper.map(entity, MembershipDto.class))
                .toList();
    }

    public MembershipDto update(final UUID id, final MembershipRequest request) {
        return repository.findById(id)
                .map(entity -> {
                    // Editar entidade
                    mapper.map(request, entity);

                    // Salvar
                    final var saved = repository.save(entity);

                    // Mapear para DTO
                    return mapper.map(saved, MembershipDto.class);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public MembershipDto changePerson(final UUID id, final UUID personId) {
        return repository.findById(id)
                .map(entity -> {
                    // Editar entidade
                    entity.setPersonId(personId);

                    // Salvar
                    final var saved = repository.save(entity);

                    // Mapear para DTO
                    return mapper.map(saved, MembershipDto.class);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

}
