package br.org.kinflasy.api.apis.churches.repositories.membership;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.apis.churches.entities.membership.Entry;

public interface EntryRepository extends JpaRepository<Entry, UUID> {

}
