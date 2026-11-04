package br.org.kinflasy.apis.calendar.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.calendar.entities.CalendarEvent;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, UUID> {

}
