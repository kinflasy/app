package br.org.kinflasy.apis.people_filters.repositories;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.people_filters.entities.StaticPeopleFilter;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;

public interface StaticPeopleFilterRepository extends JpaRepository<StaticPeopleFilter, UUID> {

    public Optional<StaticPeopleFilter> findByCharacteristic(final PersonCharacteristic characteristic);

    public default StaticPeopleFilter findOrCreate(final StaticPeopleFilter filter) {
        final var optional = findByCharacteristic(filter.getCharacteristic());

        try {
            final var existing = optional.get();

            if (existing != null) {
                return existing;
            } else {
                throw new NoSuchElementException();
            }
        } catch (final NoSuchElementException e) {
            return save(filter);
        }

    }
}
