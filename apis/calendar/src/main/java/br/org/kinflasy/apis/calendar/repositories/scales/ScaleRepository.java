package br.org.kinflasy.apis.calendar.repositories.scales;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.calendar.entities.scales.Scale;

public interface ScaleRepository extends JpaRepository<Scale, UUID> {

}
