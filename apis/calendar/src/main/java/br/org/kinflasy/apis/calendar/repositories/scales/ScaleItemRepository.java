package br.org.kinflasy.apis.calendar.repositories.scales;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.calendar.entities.scales.ScaleItem;

@Repository
public interface ScaleItemRepository extends JpaRepository<ScaleItem, UUID> {

    List<ScaleItem> findByScaleId(UUID scaleId);

    List<ScaleItem> findByPersonId(UUID personId);

    Optional<ScaleItem> findByScaleIdAndPersonIdAndRoleId(UUID scaleId, UUID personId, UUID roleId);

    @Query("""
            SELECT si FROM ScaleItem si
            JOIN OwnerScale os ON os.id = si.scaleId
            JOIN CalendarEvent ce ON ce.id = os.calendarEventId
            WHERE si.personId = :personId AND
                (ce.startDateTime BETWEEN :start AND :end
                OR ce.endDateTime BETWEEN :start AND :end)
            """)
    List<ScaleItem> findOwnerScalesByPersonIdInRange(UUID personId, final LocalDateTime start, final LocalDateTime end);

    @Query("""
            SELECT si FROM ScaleItem si
            JOIN CollaboratorScale cs ON cs.id = si.scaleId
            JOIN EventCollaboration ec ON ec.id = cs.collaborationId
            JOIN CalendarEvent ce ON ce.id = ec.calendarEventId
            WHERE si.personId = :personId AND
                (ce.startDateTime BETWEEN :start AND :end
                OR ce.endDateTime BETWEEN :start AND :end)
            """)
    List<ScaleItem> findCollaboratorScalesByPersonIdInRange(UUID personId, final LocalDateTime start,
            final LocalDateTime end);

}
