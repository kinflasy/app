package br.org.kinflasy.apis.churches.repositories.department;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.department.Integration;
import java.util.List;

public interface IntegrationRepository extends JpaRepository<Integration, UUID> {

    List<Integration> findByDepartmentId(UUID departmentId);

    List<Integration> findByPersonId(UUID personId);

    List<Integration> findByDepartmentIdAndPersonId(UUID departmentId, UUID personId);

}
