package br.org.kinflasy.apis.people_filters.services;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people_filters.factories.ConditionFactory;
import br.org.kinflasy.apis.people_filters.repositories.ConditionRepository;
import br.org.kinflasy.libs.people_filters.dto.ConditionRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConditionService {

    private final ConditionRepository repository;

    private final ConditionFactory factory;

    public void create(final ConditionRequest request) {
        final var entity = factory.toEntity(request.getCondition());
        repository.save(entity);
    }

}
