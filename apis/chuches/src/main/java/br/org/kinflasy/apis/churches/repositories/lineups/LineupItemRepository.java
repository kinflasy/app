package br.org.kinflasy.apis.churches.repositories.lineups;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.churches.entities.lineups.LineupItem;

@Repository
public interface LineupItemRepository extends JpaRepository<LineupItem, UUID> {

    List<LineupItem> findByLineupId(UUID id);

}
