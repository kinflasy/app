package br.org.kinflasy.apis.people_filters.predicates.structure;

import java.util.function.BiPredicate;

import org.springframework.stereotype.Component;

import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.structure.Condition;

@Component
public interface ConditionPredicate<C extends Condition> extends BiPredicate<C, PersonDto> {

    public abstract boolean test(final C condition, final PersonDto person);

}
