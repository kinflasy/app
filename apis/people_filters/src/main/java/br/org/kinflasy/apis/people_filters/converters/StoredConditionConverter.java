package br.org.kinflasy.apis.people_filters.converters;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.entities.StoredAndConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredNegativeCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredOrConditionGroup;
import br.org.kinflasy.apis.people_filters.factories.ConditionFactory;
import br.org.kinflasy.libs.base_conditions.conditions.logical.AndConditionGroup;
import br.org.kinflasy.libs.base_conditions.conditions.logical.NegativeCondition;
import br.org.kinflasy.libs.base_conditions.conditions.logical.OrConditionGroup;
import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.base_conditions.conditions.structure.ConditionGroup;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StoredConditionConverter {

    private final ModelMapper mapper;
    private final ConditionFactory factory;

    @SuppressWarnings("unchecked")
    public <D extends Condition, E extends StoredCondition> E toEntity(final D source) {
        // Fluxo para quando a condição for um agrupamento
        if (source instanceof ConditionGroup group) {
            // Transformar lista interna
            final var inner = group.getConditions().stream()
                    .map((Function<Condition, StoredCondition>) this::toEntity)
                    .toList();

            // Encapsular
            final E result;
            if (source instanceof AndConditionGroup) {
                final var andGroup = new StoredAndConditionGroup();
                andGroup.getConditions().addAll(inner);
                result = (E) andGroup;
            } else if (source instanceof OrConditionGroup) {
                final var orGroup = new StoredOrConditionGroup();
                orGroup.getConditions().addAll(inner);
                result = (E) orGroup;
            } else {
                throw new IllegalStateException("Não encontrado grupo correspondente");
            }

            return result;
        }

        // Fluxo para quando a condição for negativa
        if (source instanceof NegativeCondition negative) {
            // Transformar condição interna
            final var base = toEntity(negative.getBaseCondition());

            // Encapsular
            return (E) new StoredNegativeCondition(base);
        }

        // Fluxo para quando a condição não for lógica
        final var entityClass = (Class<E>) factory.getEntityClass(source.getClass());
        final var entity = mapper.map(source, entityClass);
        entity.setId(null);
        return entity;
    }

    @SuppressWarnings("unchecked")
    public <D extends Condition, E extends StoredCondition> D toDto(final E source) {
        // Fluxo para quando a condição for um agrupamento
        if (source instanceof StoredConditionGroup group) {
            // Transformar lista interna
            final var inner = group.getConditions().stream()
                    .map((Function<StoredCondition, Condition>) this::toDto)
                    .toList();

            // Encapsular
            final D result;
            if (source instanceof StoredAndConditionGroup) {
                final var andGroup = new AndConditionGroup();
                andGroup.getConditions().addAll(inner);
                result = (D) andGroup;
            } else if (source instanceof StoredOrConditionGroup) {
                final var orGroup = new OrConditionGroup();
                orGroup.getConditions().addAll(inner);
                result = (D) orGroup;
            } else {
                throw new IllegalStateException("Não encontrado grupo correspondente");
            }

            return result;
        }

        // Fluxo para quando a condição for negativa
        if (source instanceof StoredNegativeCondition negative) {
            // Transformar condição interna
            final var base = toDto(negative.getBaseCondition());

            // Encapsular
            return (D) new NegativeCondition(base);
        }

        final var dtoClass = factory.getConditionClass(source.getClass());
        return (D) mapper.map(source, dtoClass);
    }

}
