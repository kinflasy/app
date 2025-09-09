package br.org.kinflasy.apis.people.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people.entities.Person;
import br.org.kinflasy.libs.api_utils.Converter;
import br.org.kinflasy.libs.people.dto.PersonDto;

@Component
public class PersonConverter extends Converter<Person, PersonDto> {

    public PersonConverter(final ModelMapper mapper) {
        super(mapper, Person.class, PersonDto.class);
    }

}
