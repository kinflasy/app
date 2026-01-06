package br.org.kinflasy.apis.people_filters.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people_filters.converters.StoredConditionConverter;
import br.org.kinflasy.apis.people_filters.entities.StoredCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredNegativeCondition;
import br.org.kinflasy.apis.people_filters.factories.ConditionFactory;
import br.org.kinflasy.apis.people_filters.repositories.GeneralConditionRepository;
import br.org.kinflasy.libs.base_conditions.conditions.logical.NegativeCondition;
import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.base_conditions.conditions.structure.ConditionGroup;
import br.org.kinflasy.libs.base_conditions.dto.StoredConditionDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConditionService {

    private final GeneralConditionRepository generalRepository;

    private final ConditionFactory factory;
    private final StoredConditionConverter converter;

    public <C extends Condition> StoredConditionDto<C> findOrCreate(final C condition) {
        final var saved = processFindOrCreate(condition);
        return new StoredConditionDto<>(saved.getId(), condition);
    }

    @SuppressWarnings("unchecked")
    private <E extends StoredCondition> E processFindOrCreate(final Condition condition) {
        final var repository = factory.getRepository(condition);

        if (condition instanceof ConditionGroup group) {
            final List<StoredCondition> savedInner = group.getConditions().stream()
                    .map((Function<Condition, StoredCondition>) this::processFindOrCreate)
                    .toList();

            final StoredConditionGroup entity = converter.toEntity(condition);
            entity.getConditions().clear();
            entity.getConditions().addAll(savedInner);

            return (E) repository.findOrCreate(entity);
        }

        if (condition instanceof NegativeCondition negative) {
            final var savedInner = processFindOrCreate(negative.getBaseCondition());

            final var entity = (StoredNegativeCondition) converter.toEntity(condition);
            entity.setBaseCondition(savedInner);

            return (E) repository.findOrCreate(entity);
        }

        final var entity = converter.toEntity(condition);
        return (E) repository.findOrCreate(entity);
    }

    public <C extends Condition> Optional<C> findById(final UUID id) {
        return generalRepository.findById(id).map(converter::toDto);
    }

}
