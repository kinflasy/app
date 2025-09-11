package br.org.kinflasy.apis.churches.repositories.department;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.department.Department;
import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    List<Department> findByUnitId(UUID unitId);

}
