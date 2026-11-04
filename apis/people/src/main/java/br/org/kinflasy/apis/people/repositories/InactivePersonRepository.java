package br.org.kinflasy.apis.people.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.people.entities.InactivePerson;

public interface InactivePersonRepository extends JpaRepository<InactivePerson, UUID> {

}
