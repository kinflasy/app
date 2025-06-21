package br.org.kinflasy.api.repositories.core.people_filter;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.people_filter.StaticPeopleFilter;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;

public interface StaticPeopleFilterRepository extends JpaRepository<StaticPeopleFilter, Integer> {

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
