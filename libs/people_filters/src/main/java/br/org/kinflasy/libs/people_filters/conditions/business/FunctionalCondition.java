package br.org.kinflasy.libs.people_filters.conditions.business;

import java.util.function.Predicate;

import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import lombok.Data;

@Data
public class FunctionalCondition implements Condition {

    private final Predicate<PersonDto> predicate;

}
