package br.org.kinflasy.apis.calendar.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.org.kinflasy.apis.calendar.entities.EventCollaboration;

public interface EventCollaborationRepository extends JpaRepository<EventCollaboration, UUID> {

    List<EventCollaboration> findByCalendarEventId(UUID calendarEventId);

    List<EventCollaboration> findByDepartmentId(UUID departmentId);

    @Query("""
            SELECT c FROM EventCollaboration c
            JOIN CalendarEvent e ON c.calendarEventId = e.id
            WHERE c.departmentId = :departmentId
            AND
                (e.startDateTime BETWEEN :rangeStart AND :rangeEnd
                OR e.endDateTime BETWEEN :rangeStart AND :rangeEnd)
            """)
    List<EventCollaboration> findByDepartmentIdInRange(UUID departmentId, LocalDateTime rangeStart,
            LocalDateTime rangeEnd);

    Optional<EventCollaboration> findByCalendarEventIdAndDepartmentId(UUID calendarEventId, UUID departmentId);

}
