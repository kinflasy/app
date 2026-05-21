package br.org.kinflasy.apis.people.services.roles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people.entities.roles.Ability;
import br.org.kinflasy.apis.people.repositories.roles.AbilityRepository;
import br.org.kinflasy.apis.people.services.PersonService;
import br.org.kinflasy.libs.api_utils.AuthUtils;
import br.org.kinflasy.libs.people.dto.roles.AbilityDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AbilityService {

    private final ModelMapper modelMapper;

    private final AbilityRepository repository;

    private final RoleService roleService;
    private final PersonService personService;

    private final AuthUtils authUtils;

    /*
     * ACESSO AUTENTICADO
     */

    @PreAuthorize("isAuthenticated()")
    public List<AbilityDto.DetailingRole> findAll() {
        return findAllByUser(authUtils.getLoggedUser().getId());
    }

    /*
     * ACESSO RESTRITO
     */

    @PostFilter("@fga.check('ability', filterObject.id, 'can_view', 'user', principal.id)")
    public List<AbilityDto.DetailingRole> findAllByUser(final UUID personId) {
        return repository.findAllByPersonId(personId).stream()
                .map(ability -> {
                    final var dto = modelMapper.map(ability, AbilityDto.DetailingRole.class);

                    return roleService.findById(ability.getRoleId())
                            .map(dto::setRole)
                            .orElse(dto);
                })
                .toList();
    }

    @PreAuthorize("#personId.equals(principal.id) or "
            + " @fga.check('user', #personId, 'admin', 'user', principal.id) or "
            + " @fga.check('user', #personId, 'assistant', 'user', principal.id)")
    public Optional<AbilityDto> create(final UUID personId, final UUID roleId) {
        // Assegurar que a pessoa existe
        return personService.findById(personId)

                // Assegurar que o papel existe
                .flatMap(person -> roleService.findById(roleId))

                // Criar a habilidade
                .map(role -> {
                    final var entity = new Ability(null, personId, roleId);
                    final var ability = repository.save(entity);
                    return modelMapper.map(ability, AbilityDto.class);
                });
    }

    @PreAuthorize("@fga.check('ability', #id, 'can_edit', 'user', principal.id)")
    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
