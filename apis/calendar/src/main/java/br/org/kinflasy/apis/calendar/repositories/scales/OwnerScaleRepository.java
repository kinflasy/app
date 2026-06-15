package br.org.kinflasy.apis.calendar.repositories.scales;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.calendar.entities.scales.OwnerScale;

public interface OwnerScaleRepository extends JpaRepository<OwnerScale, UUID> {

    List<OwnerScale> findByCalendarEventId(final UUID calendarEventId);

}
