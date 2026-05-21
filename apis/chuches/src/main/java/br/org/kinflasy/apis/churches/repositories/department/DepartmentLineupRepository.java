package br.org.kinflasy.apis.churches.repositories.department;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.churches.entities.department.DepartmentLineup;

@Repository
public interface DepartmentLineupRepository extends JpaRepository<DepartmentLineup, UUID> {

    List<DepartmentLineup> findAllByDepartmentId(UUID departmentId);

}
