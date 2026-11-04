package br.org.kinflasy.apis.people.repositories.roles;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.people.entities.roles.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {

}
