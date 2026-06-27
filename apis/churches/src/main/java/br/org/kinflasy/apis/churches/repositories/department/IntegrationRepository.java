package br.org.kinflasy.apis.churches.repositories.department;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.department.Integration;

public interface IntegrationRepository extends JpaRepository<Integration, UUID> {

    List<Integration> findByDepartmentId(UUID departmentId);

    List<Integration> findByMembershipId(UUID membershipId);

    Optional<Integration> findByDepartmentIdAndMembershipId(UUID departmentId, UUID membershipId);

}
