package br.org.kinflasy.apis.churches.repositories.department;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.department.ExtensionSubscription;
import java.util.List;
import java.util.Optional;

import br.org.kinflasy.libs.churches.enums.department.Extension;

public interface ExtensionSubscriptionRepository extends JpaRepository<ExtensionSubscription, UUID> {

    List<ExtensionSubscription> findByDepartmentId(UUID departmentId);

    List<ExtensionSubscription> findByExtension(Extension extension);

    Optional<ExtensionSubscription> findByDepartmentIdAndExtension(UUID departmentId, Extension extension);

}
