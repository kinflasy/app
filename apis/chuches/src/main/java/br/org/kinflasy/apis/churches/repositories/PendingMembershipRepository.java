package br.org.kinflasy.apis.churches.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.PendingMembership;

import java.util.List;

public interface PendingMembershipRepository extends JpaRepository<PendingMembership, UUID> {

    List<PendingMembership> findByUnitId(UUID unitId);

    List<PendingMembership> findByPersonId(UUID personId);

    List<PendingMembership> findByUnitIdAndPersonId(UUID unitId, UUID personId);

}
