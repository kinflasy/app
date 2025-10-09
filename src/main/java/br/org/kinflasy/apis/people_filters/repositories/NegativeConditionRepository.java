package br.org.kinflasy.apis.people_filters.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredNegativeCondition;

@Repository
public interface NegativeConditionRepository extends JpaRepository<StoredNegativeCondition, UUID> {

    Optional<StoredNegativeCondition> findByBaseCondition(StoredCondition baseCondition);

    default StoredNegativeCondition findOrCreate(final StoredNegativeCondition filter) {
        return findByBaseCondition(filter.getBaseCondition())
                .orElseGet(() -> save(filter));
    }

}
