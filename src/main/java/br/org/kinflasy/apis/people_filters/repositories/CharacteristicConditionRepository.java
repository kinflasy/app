package br.org.kinflasy.apis.people_filters.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredCharacteristicCondition;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;

@Repository
public interface CharacteristicConditionRepository
        extends ConditionRepository<StoredCharacteristicCondition>, JpaRepository<StoredCharacteristicCondition, UUID> {

    Optional<StoredCharacteristicCondition> findByCharacteristic(PersonCharacteristic characteristic);

    default StoredCharacteristicCondition findOrCreate(final StoredCharacteristicCondition filter) {
        return findByCharacteristic(filter.getCharacteristic())
                .orElseGet(() -> save(filter));
    }

}
