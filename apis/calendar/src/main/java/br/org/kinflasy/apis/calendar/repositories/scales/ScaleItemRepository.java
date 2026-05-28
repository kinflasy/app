package br.org.kinflasy.apis.calendar.repositories.scales;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.calendar.entities.scales.ScaleItem;

@Repository
public interface ScaleItemRepository extends JpaRepository<ScaleItem, UUID> {

    List<ScaleItem> findByScaleId(UUID scaleId);

    List<ScaleItem> findByPersonId(UUID personId);

}
