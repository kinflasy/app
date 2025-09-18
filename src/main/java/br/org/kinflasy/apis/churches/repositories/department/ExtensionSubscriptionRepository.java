package br.org.kinflasy.apis.churches.repositories.department;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.department.ExtensionSubscription;

public interface ExtensionSubscriptionRepository extends JpaRepository<ExtensionSubscription, UUID> {

}
