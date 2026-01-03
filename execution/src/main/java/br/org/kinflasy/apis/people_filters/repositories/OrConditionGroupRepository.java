package br.org.kinflasy.apis.people_filters.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredOrConditionGroup;

@Repository
public interface OrConditionGroupRepository
        extends ConditionRepository<StoredOrConditionGroup>, JpaRepository<StoredOrConditionGroup, UUID> {

    default StoredOrConditionGroup findOrCreate(final StoredOrConditionGroup filter) {
        return save(filter);
    }

}
