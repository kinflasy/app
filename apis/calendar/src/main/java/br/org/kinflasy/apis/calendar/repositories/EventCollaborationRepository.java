package br.org.kinflasy.apis.calendar.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.calendar.entities.EventCollaboration;

@Repository
public interface EventCollaborationRepository extends JpaRepository<EventCollaboration, UUID> {

}
