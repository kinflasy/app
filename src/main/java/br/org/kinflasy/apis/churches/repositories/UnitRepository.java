package br.org.kinflasy.apis.churches.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.Unit;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

}
