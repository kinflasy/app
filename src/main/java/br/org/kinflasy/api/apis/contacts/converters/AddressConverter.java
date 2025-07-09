package br.org.kinflasy.api.apis.contacts.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.api.apis.contacts.entities.Address;
import br.org.kinflasy.api.libs.api_utils.Converter;
import br.org.kinflasy.api.libs.contacts.dto.AddressDto;

@Component
public class AddressConverter extends Converter<Address, AddressDto> {

    public AddressConverter(final ModelMapper mapper) {
        super(mapper, Address.class, AddressDto.class);
    }

}
