package br.org.kinflasy.apis.calendar.repositories.scales;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.calendar.entities.scales.OwnerScale;

@Repository
public interface OwnerScaleRepository extends JpaRepository<OwnerScale, UUID> {

}
