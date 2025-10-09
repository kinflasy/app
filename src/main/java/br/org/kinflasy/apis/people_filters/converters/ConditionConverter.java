package br.org.kinflasy.apis.people_filters.converters;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.entities.StoredCondition;
import br.org.kinflasy.apis.people_filters.factories.ConditionFactory;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ConditionConverter extends AbstractConverter<Condition, StoredCondition> {

    private final ConditionFactory conditionFactory;

    @Override
    protected StoredCondition convert(final Condition source) {
        return conditionFactory.toEntity(source);
    }

}
