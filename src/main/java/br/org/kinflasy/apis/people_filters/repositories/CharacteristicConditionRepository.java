package br.org.kinflasy.apis.people_filters.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.people_filters.entities.CharacteristicCondition;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;

public interface CharacteristicConditionRepository extends JpaRepository<CharacteristicCondition, UUID> {

    public Optional<CharacteristicCondition> findByCharacteristic(final PersonCharacteristic characteristic);

    public default CharacteristicCondition findOrCreate(final CharacteristicCondition filter) {
        return findByCharacteristic(filter.getCharacteristic())
                .orElseGet(() -> save(filter));
    }

}
