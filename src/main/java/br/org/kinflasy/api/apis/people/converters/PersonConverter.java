package br.org.kinflasy.api.apis.people.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.libs.api_utils.Converter;
import br.org.kinflasy.api.libs.people.dto.PersonDto;

@Component
public class PersonConverter extends Converter<Person, PersonDto> {

    public PersonConverter(final ModelMapper mapper) {
        super(mapper, Person.class, PersonDto.class);
    }

}
