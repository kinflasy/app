package br.org.kinflasy.apis.people_filters.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.people_filters.entities.StoredCondition;

@Repository
public interface GeneralConditionRepository extends JpaRepository<StoredCondition, UUID> {

}
