package br.org.kinflasy.api.repositories.core;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
