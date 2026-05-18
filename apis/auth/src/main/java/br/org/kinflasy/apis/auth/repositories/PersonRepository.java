package br.org.kinflasy.apis.auth.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.auth.entities.Person;

@Repository("authPersonRepository")
public interface PersonRepository extends JpaRepository<Person, UUID> {

}
