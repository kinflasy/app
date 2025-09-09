package br.org.kinflasy.apis.contacts.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.contacts.entities.Address;
import br.org.kinflasy.libs.contacts.dto.AddressDto;
import br.org.kinflasy.libs.api_utils.Converter;

@Component
public class AddressConverter extends Converter<Address, AddressDto> {

    public AddressConverter(final ModelMapper mapper) {
        super(mapper, Address.class, AddressDto.class);
    }

}
