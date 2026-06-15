package br.org.kinflasy.apis.calendar.repositories.scales;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.org.kinflasy.apis.calendar.entities.scales.ScaleItem;

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
            WHERE si.personId = :personId
            AND si.scaleId IN (
                SELECT cs.id FROM CollaboratorScale cs
                WHERE cs.collaborationId IN (
                    SELECT ec.id FROM EventCollaboration ec
                    WHERE ec.calendarEventId IN (
                        SELECT ce.id FROM CalendarEvent ce
                        WHERE ce.startDateTime < :end
                        AND ce.endDateTime > :start
                    )
                )
            )
                    """)
    List<ScaleItem> findCollaboratorScalesByPersonIdInRange(UUID personId, final LocalDateTime start,
            final LocalDateTime end);

}
