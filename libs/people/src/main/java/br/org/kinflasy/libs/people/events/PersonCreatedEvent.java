package br.org.kinflasy.libs.people.events;

import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonCreatedEvent<D extends PersonDto> {

    private final D person;

}
