package br.org.kinflasy.apis.people.repositories;

import java.time.MonthDay;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.org.kinflasy.apis.people.entities.Person;
import br.org.kinflasy.libs.people.ids.PersonId;

public interface PersonRepository extends JpaRepository<Person, PersonId> {

    List<Person> findByIdIn(Iterable<PersonId> ids);

    @Query("""
            SELECT p
            FROM Person p
            WHERE p.id IN :ids
                AND TO_CHAR(p.birthDate, 'MM-DD') BETWEEN :start AND :end
            """)
    List<Person> findByIdInAndBirthdateInRange(Iterable<PersonId> ids, String start, String end);

    default List<Person> findByIdInAndBirthdateInRange(final Iterable<PersonId> ids, final MonthDay start,
            final MonthDay end) {
        final var startStr = String.format("%02d-%02d", start.getMonthValue(), start.getDayOfMonth());
        final var endStr = String.format("%02d-%02d", end.getMonthValue(), end.getDayOfMonth());
        return findByIdInAndBirthdateInRange(ids, startStr, endStr);
    }

}
