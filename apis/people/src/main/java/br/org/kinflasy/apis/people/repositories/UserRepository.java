package br.org.kinflasy.apis.people.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.people.entities.User;
import br.org.kinflasy.libs.people.ids.PersonId;


public interface UserRepository extends JpaRepository<User, PersonId> {

    Optional<User> findByUsername(String username);

}
