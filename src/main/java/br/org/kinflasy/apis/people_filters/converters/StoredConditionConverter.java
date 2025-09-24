package br.org.kinflasy.apis.people_filters.converters;

import org.modelmapper.ModelMapper;

import br.org.kinflasy.apis.people_filters.entities.StoredCondition;
import br.org.kinflasy.apis.people_filters.factories.ConditionPredicateFactory;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StoredConditionConverter {

    private final ModelMapper mapper;
    private final ConditionPredicateFactory factory;

    @SuppressWarnings("unchecked")
    public <D extends Condition, E extends StoredCondition> E toEntity(final D source) {
        final var entityClass = factory.getEntityClass(source.getClass());
        return (E) mapper.map(source, entityClass);
    }

    @SuppressWarnings("unchecked")
    public <D extends Condition, E extends StoredCondition> D toDto(final E source) {
        final var dtoClass = factory.getConditionClass(source.getClass());
        return (D) mapper.map(source, dtoClass);
    }

    public <D extends Condition, E extends StoredCondition> E toEntity(final D source, final E destiny) {
        mapper.map(source, destiny);
        return destiny;
    }

    public <D extends Condition, E extends StoredCondition> D toDto(final E source, final D destiny) {
        mapper.map(source, destiny);
        return destiny;
    }

}
