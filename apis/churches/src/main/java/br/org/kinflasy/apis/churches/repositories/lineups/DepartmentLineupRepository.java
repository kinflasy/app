package br.org.kinflasy.apis.churches.repositories.lineups;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.churches.entities.lineups.DepartmentLineup;

public interface DepartmentLineupRepository extends JpaRepository<DepartmentLineup, UUID> {

    List<DepartmentLineup> findAllByDepartmentId(UUID departmentId);

}
