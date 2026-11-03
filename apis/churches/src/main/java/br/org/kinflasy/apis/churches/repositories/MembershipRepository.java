package br.org.kinflasy.apis.churches.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.churches.entities.Membership;
import br.org.kinflasy.libs.people.ids.PersonId;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {

    List<Membership> findByUnitId(UUID unitId);

    List<Membership> findByUnitIdAndLeaveDateNull(UUID unitId);

    List<Membership> findByPersonId(PersonId personId);

    List<Membership> findByPersonIdAndLeaveDateNull(PersonId personId);

    List<Membership> findByUnitIdAndPersonId(UUID unitId, PersonId personId);

    Optional<Membership> findByUnitIdAndPersonIdAndLeaveDateNull(UUID unitId, PersonId personId);

}
