package br.org.kinflasy.api.repositories.core.church.membership;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.church.membership.Entry;

public interface EntryRepository extends JpaRepository<Entry, Integer> {

}
