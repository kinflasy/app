package br.org.kinflasy.libs.people.events;

import br.org.kinflasy.libs.people.dto.UserDto;

public class UserCreatedEvent extends PersonCreatedEvent<UserDto> {

    public UserCreatedEvent(final UserDto person) {
        super(person);
    }

}
