package br.org.kinflasy.apis.people_filters.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredAndConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredCondition;

@Repository
public interface AndConditionGroupRepository extends JpaRepository<StoredAndConditionGroup, UUID> {

    Optional<StoredAndConditionGroup> findByConditions(List<StoredCondition> conditions);

    default StoredAndConditionGroup findOrCreate(final StoredAndConditionGroup filter) {
        return findByConditions(filter.getConditions())
                .orElseGet(() -> save(filter));
    }

}
