package br.org.kinflasy.apis.people_filters.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredOrConditionGroup;

@Repository
public interface OrConditionGroupRepository extends JpaRepository<StoredOrConditionGroup, UUID> {

    Optional<StoredOrConditionGroup> findByConditions(List<StoredCondition> conditions);

    default StoredOrConditionGroup findOrCreate(final StoredOrConditionGroup filter) {
        return findByConditions(filter.getConditions())
                .orElseGet(() -> save(filter));
    }

}
