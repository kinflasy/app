package br.org.kinflasy.api.apis.churches.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.api.apis.churches.entities.Unit;
import br.org.kinflasy.api.libs.api_utils.Converter;
import br.org.kinflasy.api.libs.churches.dto.UnitDto;

@Component
public class UnitConverter extends Converter<Unit, UnitDto> {

    public UnitConverter(final ModelMapper mapper) {
        super(mapper, Unit.class, UnitDto.class);
    }

}
