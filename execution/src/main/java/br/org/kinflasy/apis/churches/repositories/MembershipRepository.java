package br.org.kinflasy.apis.churches.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.Membership;
import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {

    List<Membership> findByUnitId(UUID unitId);

    List<Membership> findByPersonId(UUID personId);

    List<Membership> findByUnitIdAndPersonId(UUID unitId, UUID personId);

}
