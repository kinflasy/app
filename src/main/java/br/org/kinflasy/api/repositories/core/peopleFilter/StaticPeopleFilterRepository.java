package br.org.kinflasy.api.repositories.core.peopleFilter;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.peopleFilter.StaticPeopleFilter;

public interface StaticPeopleFilterRepository extends JpaRepository<StaticPeopleFilter, Integer> {

}
