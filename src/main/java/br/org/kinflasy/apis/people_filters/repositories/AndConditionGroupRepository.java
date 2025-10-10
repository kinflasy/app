package br.org.kinflasy.apis.people_filters.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredAndConditionGroup;

@Repository
public interface AndConditionGroupRepository
        extends ConditionRepository<StoredAndConditionGroup>, JpaRepository<StoredAndConditionGroup, UUID> {

    default StoredAndConditionGroup findOrCreate(final StoredAndConditionGroup condition) {
        return saveAndFlush(condition);
    }

}
