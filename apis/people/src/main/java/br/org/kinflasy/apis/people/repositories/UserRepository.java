package br.org.kinflasy.apis.people.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.people.entities.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

}
