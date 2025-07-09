package br.org.kinflasy.api.apis.churches.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.apis.churches.entities.Unit;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

}
