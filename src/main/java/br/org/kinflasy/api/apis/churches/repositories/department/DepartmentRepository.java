package br.org.kinflasy.api.apis.churches.repositories.department;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.apis.churches.entities.department.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

}
