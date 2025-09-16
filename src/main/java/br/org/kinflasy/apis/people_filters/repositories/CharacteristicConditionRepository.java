package br.org.kinflasy.apis.people_filters.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.people_filters.entities.StoredCharacteristicCondition;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;

public interface CharacteristicConditionRepository extends JpaRepository<StoredCharacteristicCondition, UUID> {

    public Optional<StoredCharacteristicCondition> findByCharacteristic(final PersonCharacteristic characteristic);

    public default StoredCharacteristicCondition findOrCreate(final StoredCharacteristicCondition filter) {
        return findByCharacteristic(filter.getCharacteristic())
                .orElseGet(() -> save(filter));
    }

}
