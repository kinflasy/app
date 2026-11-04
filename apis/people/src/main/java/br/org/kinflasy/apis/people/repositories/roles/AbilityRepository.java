package br.org.kinflasy.apis.people.repositories.roles;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.people.entities.roles.Ability;

public interface AbilityRepository extends JpaRepository<Ability, UUID> {

    List<Ability> findAllByPersonId(UUID personId);

}
