package br.org.kinflasy.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.entities.DepartmentCalendarEvent;

@Repository
public interface DepartmentCalendarEventRepository extends JpaRepository<DepartmentCalendarEvent, UUID> {

    List<DepartmentCalendarEvent> findByStartDateTimeBeforeAndEndDateTimeAfter(UUID departmentId,
            LocalDateTime startDateTime, LocalDateTime endDateTime);

}
