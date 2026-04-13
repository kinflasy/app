package br.org.kinflasy.apis.contacts.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.contacts.entities.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {

}
