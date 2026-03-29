package br.org.kinflasy.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.entities.UnitCalendarEvent;

@Repository
public interface UnitCalendarEventRepository extends JpaRepository<UnitCalendarEvent, UUID> {

    List<UnitCalendarEvent> findByUnitIdAndStartDateTimeBeforeAndEndDateTimeAfter(UUID unitId,
            LocalDateTime startDateTime, LocalDateTime endDateTime);

}
