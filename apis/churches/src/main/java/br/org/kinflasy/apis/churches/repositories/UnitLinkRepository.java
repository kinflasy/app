package br.org.kinflasy.apis.churches.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.churches.entities.UnitLink;
import br.org.kinflasy.libs.churches.id.UnitLinkId;

public interface UnitLinkRepository extends JpaRepository<UnitLink, UnitLinkId> {

    List<UnitLink> findByUnitId(UUID unitId);

}
