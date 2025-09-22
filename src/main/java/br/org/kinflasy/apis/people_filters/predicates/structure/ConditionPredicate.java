package br.org.kinflasy.apis.people_filters.predicates.structure;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.Data;

@Data
@Component
public abstract class ConditionPredicate implements Predicate<PersonDto> {

}
