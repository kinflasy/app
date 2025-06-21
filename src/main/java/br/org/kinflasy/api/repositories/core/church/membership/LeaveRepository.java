package br.org.kinflasy.api.repositories.core.church.membership;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.church.membership.Leave;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {

}
