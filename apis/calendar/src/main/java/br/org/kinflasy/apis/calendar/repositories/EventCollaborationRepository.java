package br.org.kinflasy.apis.calendar.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.calendar.entities.EventCollaboration;

@Repository
public interface EventCollaborationRepository extends JpaRepository<EventCollaboration, UUID> {

    List<EventCollaboration> findByCalendarEventId(UUID calendarEventId);

    Optional<EventCollaboration> findByCalendarEventIdAndDepartmentId(UUID calendarEventId, UUID departmentId);

}
