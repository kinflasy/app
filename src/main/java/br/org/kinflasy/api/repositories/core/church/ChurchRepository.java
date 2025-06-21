package br.org.kinflasy.api.repositories.core.church;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.church.Church;

public interface ChurchRepository extends JpaRepository<Church, UUID> {

}
