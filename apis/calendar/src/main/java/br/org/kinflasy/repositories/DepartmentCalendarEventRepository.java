package br.org.kinflasy.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.entities.DepartmentCalendarEvent;

@Repository
public interface DepartmentCalendarEventRepository extends JpaRepository<DepartmentCalendarEvent, UUID> {

    @Query("""
            SELECT e FROM DepartmentCalendarEvent e
            WHERE e.departmentId = :departmentId
            AND
                (e.startDateTime BETWEEN :rangeStart AND :rangeEnd
                OR e.endDateTime BETWEEN :rangeStart AND :rangeEnd)
            """)
    List<DepartmentCalendarEvent> findByDepartmentIdInRange(UUID departmentId, LocalDateTime rangeStart,
            LocalDateTime rangeEnd);

}
