package br.org.kinflasy.apis.people.repositories.roles;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people.entities.roles.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

}
