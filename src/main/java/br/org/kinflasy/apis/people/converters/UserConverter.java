package br.org.kinflasy.apis.people.converters;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people.entities.User;
import br.org.kinflasy.libs.api_utils.Converter;
import br.org.kinflasy.libs.people.dto.UserDto;

@Component
public class UserConverter extends Converter<User, UserDto> {

    public UserConverter(final ModelMapper mapper) {
        super(mapper, User.class, UserDto.class);
    }

}
