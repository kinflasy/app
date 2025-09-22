package br.org.kinflasy.apis.people_filters.conditions.structure;

import java.util.function.Predicate;

import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.Data;

@Data
public abstract class Condition implements Predicate<PersonDto> {

}
