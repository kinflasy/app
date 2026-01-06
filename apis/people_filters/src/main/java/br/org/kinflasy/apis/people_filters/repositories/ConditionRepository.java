package br.org.kinflasy.apis.people_filters.repositories;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.entities.StoredCondition;

@Component
public interface ConditionRepository<E extends StoredCondition> {

    E findOrCreate(final E condition);

}
