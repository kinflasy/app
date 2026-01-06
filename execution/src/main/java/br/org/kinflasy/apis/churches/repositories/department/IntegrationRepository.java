package br.org.kinflasy.apis.churches.repositories.department;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.department.Integration;
import java.util.List;

public interface IntegrationRepository extends JpaRepository<Integration, UUID> {

    List<Integration> findByDepartmentId(UUID departmentId);

    List<Integration> findByMembershipId(UUID membershipId);

    List<Integration> findByDepartmentIdAndMembershipId(UUID departmentId, UUID membershipId);

}
