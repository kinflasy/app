package br.org.kinflasy.api.repositories.core.church.department;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.church.department.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

}
