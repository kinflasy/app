package br.org.kinflasy.apis.people_filters.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredUnitMembershipCondition;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;

@Repository
public interface UnitMembershipConditionRepository
        extends ConditionRepository<StoredUnitMembershipCondition>, JpaRepository<StoredUnitMembershipCondition, UUID> {

    Optional<StoredUnitMembershipCondition> findByUnitIdAndAffiliation(UUID unitId, Affiliation affiliation);

    default StoredUnitMembershipCondition findOrCreate(final StoredUnitMembershipCondition condition) {
        return findByUnitIdAndAffiliation(condition.getUnitId(), condition.getAffiliation())
                .orElseGet(() -> save(condition));
    }

}
