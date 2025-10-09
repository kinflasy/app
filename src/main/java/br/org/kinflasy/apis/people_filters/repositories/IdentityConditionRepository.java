package br.org.kinflasy.apis.people_filters.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredIdentityCondition;

@Repository
public interface IdentityConditionRepository extends JpaRepository<StoredIdentityCondition, UUID> {

    Optional<StoredIdentityCondition> findByPersonId(UUID personId);

    default StoredIdentityCondition findOrCreate(final StoredIdentityCondition filter) {
        return findByPersonId(filter.getPersonId())
                .orElseGet(() -> save(filter));
    }

}
