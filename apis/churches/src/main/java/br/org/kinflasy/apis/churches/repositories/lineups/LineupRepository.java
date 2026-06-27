package br.org.kinflasy.apis.churches.repositories.lineups;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.churches.entities.lineups.Lineup;

public interface LineupRepository extends JpaRepository<Lineup, UUID> {

}
