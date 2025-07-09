package br.org.kinflasy.api.apis.people.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.api.apis.people.entities.InactivePerson;
import br.org.kinflasy.api.libs.api_utils.Converter;
import br.org.kinflasy.api.libs.people.dto.InactivePersonDto;

@Component
public class InactivePersonConverter extends Converter<InactivePerson, InactivePersonDto> {

    public InactivePersonConverter(final ModelMapper mapper) {
        super(mapper, InactivePerson.class, InactivePersonDto.class);
    }

}
