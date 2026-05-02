package br.org.kinflasy.apis.people.services.roles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.github.slugify.Slugify;

import br.org.kinflasy.apis.people.entities.roles.Role;
import br.org.kinflasy.apis.people.repositories.roles.RoleRepository;
import br.org.kinflasy.libs.people.dto.roles.RoleDto;
import br.org.kinflasy.libs.people.dto.roles.RoleRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {

    private final ModelMapper modelMapper;
    private final Slugify slugify;

    private final RoleRepository repository;

    /*
     * ACESSO PÚBLICO
     */

    public List<RoleDto> findAll() {
        return repository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleDto.class))
                .toList();
    }

    public Optional<RoleDto> findById(final UUID id) {
        return repository.findById(id)
                .map(role -> modelMapper.map(role, RoleDto.class));
    }

    /*
     * ACESSO AUTENTICADO
     */
    @PreAuthorize("isAuthenticated()")
    public RoleDto create(final RoleRequest request) {
        // Construir entidade (gerar slug automaticamente)
        final var entity = modelMapper.map(request, Role.class);
        entity.setSlug(slugify.slugify(request.getName()));

        // Salvar
        final var saved = repository.save(entity);

        return modelMapper.map(saved, RoleDto.class);
    }

    /*
     * ACESSO DA MANUTENÇÃO DO SISTEMA
     */
    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
