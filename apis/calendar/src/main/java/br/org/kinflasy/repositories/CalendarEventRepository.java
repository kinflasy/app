package br.org.kinflasy.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.entities.CalendarEvent;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, UUID> {

    List<CalendarEvent> findByUnitIdAndStartDateTimeBeforeAndEndDateTimeAfter(UUID unitId, LocalDateTime startDateTime,
            LocalDateTime endDateTime);

    List<CalendarEvent> findByDepartmentIdAndStartDateTimeBeforeAndEndDateTimeAfter(UUID departmentId,
            LocalDateTime startDateTime, LocalDateTime endDateTime);

}
