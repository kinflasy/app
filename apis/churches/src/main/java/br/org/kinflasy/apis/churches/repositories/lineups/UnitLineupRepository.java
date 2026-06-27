package br.org.kinflasy.apis.churches.repositories.lineups;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.churches.entities.lineups.UnitLineup;

public interface UnitLineupRepository extends JpaRepository<UnitLineup, UUID> {

    List<UnitLineup> findAllByUnitId(UUID unitId);

}
