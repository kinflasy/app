package br.org.kinflasy.apis.people.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.people.entities.InactivePerson;
import br.org.kinflasy.libs.people.ids.PersonId;

public interface InactivePersonRepository extends JpaRepository<InactivePerson, PersonId> {

}
