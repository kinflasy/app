package br.org.kinflasy.libs.people.events;

import br.org.kinflasy.libs.people.dto.UserDto;

public interface UserEvent extends PersonEvent<UserDto> {

    public static class Created extends PersonEvent.Created<UserDto> implements UserEvent {
        public Created(final UserDto person) {
            super(person);
        }
    }

}
