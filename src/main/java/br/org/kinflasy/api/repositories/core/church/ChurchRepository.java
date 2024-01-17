package br.org.kinflasy.api.repositories.core.church;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.church.Church;

public interface ChurchRepository extends JpaRepository<Church, Integer> {

}
