package br.org.kinflasy.apis.people_filters.repositories;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredExtensionIntegrationInUnitCondition;

@Repository
public interface ExtensionIntegrationInUnitConditionRepository
        extends JpaRepository<StoredExtensionIntegrationInUnitCondition, UUID> {

    default StoredExtensionIntegrationInUnitCondition findOrCreate(
            final StoredExtensionIntegrationInUnitCondition condition) {
        return findOne(Example.of(condition))
                .orElseGet(() -> save(condition));
    }

}
