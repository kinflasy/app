package br.org.kinflasy.apis.calendar.repositories.scales;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.calendar.entities.scales.CollaboratorScale;

@Repository
public interface CollaboratorScaleRepository extends JpaRepository<CollaboratorScale, UUID> {

    List<CollaboratorScale> findByCollaborationId(final UUID collaborationId);

    @Query("""
            SELECT s FROM CollaboratorScale s
            JOIN EventCollaboration c ON s.collaborationId = c.id
            WHERE c.calendarEventId = :calendarEventId
            AND c.departmentId = :departmentId
            """)
    List<CollaboratorScale> findByCalendarEventIdAndDepartmentId(final UUID calendarEventId, final UUID departmentId);

}
