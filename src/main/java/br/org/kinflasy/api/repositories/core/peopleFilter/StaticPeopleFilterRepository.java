package br.org.kinflasy.api.repositories.core.peoplefilter;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.peoplefilter.StaticPeopleFilter;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;

public interface StaticPeopleFilterRepository extends JpaRepository<StaticPeopleFilter, Integer> {

    public Optional<StaticPeopleFilter> findByCharacteristic(final @NonNull PersonCharacteristic characteristic);

    public default @NonNull StaticPeopleFilter findOrCreate(final @NonNull StaticPeopleFilter filter) {
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
