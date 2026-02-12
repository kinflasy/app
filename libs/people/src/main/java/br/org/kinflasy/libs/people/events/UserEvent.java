package br.org.kinflasy.libs.people.events;

import br.org.kinflasy.libs.people.dto.UserDto;

public interface UserEvent extends PersonEvent<UserDto> {

    public static class Created extends PersonEvent.Created<UserDto> implements UserEvent {
        public Created(final UserDto user) {
            super(user);
        }
    }

    public static class Updated extends PersonEvent.Updated<UserDto> implements UserEvent {
        public Updated(final UserDto original, final UserDto modified) {
            super(original, modified);
        }
    }

    public static class Deleted extends PersonEvent.Deleted<UserDto> implements UserEvent {
        public Deleted(final UserDto user) {
            super(user);
        }
    }

}
