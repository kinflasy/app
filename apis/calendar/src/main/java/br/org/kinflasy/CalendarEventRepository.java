package br.org.kinflasy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.entities.CalendarEvent;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, UUID> {

}
