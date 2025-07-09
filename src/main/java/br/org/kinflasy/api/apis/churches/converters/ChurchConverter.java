package br.org.kinflasy.api.apis.churches.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.api.apis.churches.entities.Church;
import br.org.kinflasy.api.libs.api_utils.Converter;
import br.org.kinflasy.api.libs.churches.dto.ChurchDto;

@Component
public class ChurchConverter extends Converter<Church, ChurchDto> {

    public ChurchConverter(final ModelMapper mapper) {
        super(mapper, Church.class, ChurchDto.class);
    }

}
