package br.org.kinflasy.api.repositories.core.church;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.church.Unit;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

}
