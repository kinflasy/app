package br.org.kinflasy.apis.auth.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.auth.entities.User;

@Repository("authUserRepository")
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

}
