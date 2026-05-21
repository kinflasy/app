package br.org.kinflasy.apis.churches.repositories.department;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.churches.entities.department.UnitLineup;

@Repository
public interface UnitLineupRepository extends JpaRepository<UnitLineup, UUID> {

    List<UnitLineup> findAllByUnitId(UUID unitId);

}
