package br.org.kinflasy.apis.people_filters.repositories;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredExtensionIntegrationInChurchCondition;

@Repository
public interface ExtensionIntegrationInChurchConditionRepository
        extends JpaRepository<StoredExtensionIntegrationInChurchCondition, UUID> {

    default StoredExtensionIntegrationInChurchCondition findOrCreate(
            final StoredExtensionIntegrationInChurchCondition condition) {
        return findOne(Example.of(condition))
                .orElseGet(() -> save(condition));
    }

}
