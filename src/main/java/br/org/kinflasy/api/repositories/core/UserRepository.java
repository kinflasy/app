package br.org.kinflasy.api.repositories.core;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
