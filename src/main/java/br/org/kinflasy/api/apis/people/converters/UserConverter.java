package br.org.kinflasy.api.apis.people.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.libs.api_utils.Converter;
import br.org.kinflasy.api.libs.people.dto.UserDto;

@Component
public class UserConverter extends Converter<User, UserDto> {

    public UserConverter(final ModelMapper mapper) {
        super(mapper, User.class, UserDto.class);
    }

}
