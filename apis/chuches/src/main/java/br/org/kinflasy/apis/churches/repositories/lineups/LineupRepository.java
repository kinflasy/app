package br.org.kinflasy.apis.churches.repositories.lineups;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.churches.entities.lineups.Lineup;

@Repository
public interface LineupRepository extends JpaRepository<Lineup, UUID> {

}
