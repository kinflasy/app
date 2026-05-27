package br.org.kinflasy.apis.calendar.repositories.scales;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.calendar.entities.scales.CollaboratorScale;

@Repository
public interface CollaboratorScaleRepository extends JpaRepository<CollaboratorScale, UUID> {

    List<CollaboratorScale> findByCollaborationId(final UUID collaborationId);

}
