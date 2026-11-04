package br.org.kinflasy.apis.churches.repositories.department;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.department.PendingIntegration;

public interface PendingIntegrationRepository extends JpaRepository<PendingIntegration, UUID> {

    List<PendingIntegration> findByDepartmentId(UUID departmentId);

    List<PendingIntegration> findByMembershipId(UUID membershipId);

    Optional<PendingIntegration> findByDepartmentIdAndMembershipId(UUID departmentId, UUID membershipId);

}
