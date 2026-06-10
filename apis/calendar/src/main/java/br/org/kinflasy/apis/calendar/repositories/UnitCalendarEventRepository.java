package br.org.kinflasy.apis.calendar.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.calendar.entities.UnitCalendarEvent;

@Repository
public interface UnitCalendarEventRepository extends JpaRepository<UnitCalendarEvent, UUID> {

    @Query("""
            SELECT e FROM UnitCalendarEvent e
            WHERE e.unitId = :unitId
            AND
                (e.startDateTime BETWEEN :rangeStart AND :rangeEnd
                OR e.endDateTime BETWEEN :rangeStart AND :rangeEnd)
            """)
    List<UnitCalendarEvent> findByUnitIdInRange(UUID unitId, LocalDateTime rangeStart, LocalDateTime rangeEnd);

}
