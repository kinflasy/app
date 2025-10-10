package br.org.kinflasy.apis.people_filters.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredChurchMembershipCondition;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;

@Repository
public interface ChurchMembershipConditionRepository extends JpaRepository<StoredChurchMembershipCondition, UUID> {

    Optional<StoredChurchMembershipCondition> findByChurchIdAndAffiliation(UUID churchId, Affiliation affiliation);

    default StoredChurchMembershipCondition findOrCreate(final StoredChurchMembershipCondition conditions) {
        return findByChurchIdAndAffiliation(conditions.getChurchId(), conditions.getAffiliation())
                .orElseGet(() -> save(conditions));
    }

}
