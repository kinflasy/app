package br.org.kinflasy.apis.churches.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.PendingMembership;
import br.org.kinflasy.libs.people.ids.PersonId;

import java.util.List;
import java.util.Optional;

public interface PendingMembershipRepository extends JpaRepository<PendingMembership, UUID> {

    List<PendingMembership> findByUnitId(UUID unitId);

    List<PendingMembership> findByPersonId(PersonId personId);

    Optional<PendingMembership> findByUnitIdAndPersonId(UUID unitId, PersonId personId);

}
