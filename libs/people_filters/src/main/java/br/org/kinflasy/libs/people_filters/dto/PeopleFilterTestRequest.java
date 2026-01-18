package br.org.kinflasy.libs.people_filters.dto;

import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.people.dto.PersonDto;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PeopleFilterTestRequest {

    private @NotNull Condition condition;
    private @NotNull PersonDto person;

}
