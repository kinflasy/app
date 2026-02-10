package br.org.kinflasy.libs.people.events;

import br.org.kinflasy.libs.people.dto.InactivePersonDto;

public interface InactivePersonEvent extends PersonEvent<InactivePersonDto> {

    public static class Created extends PersonEvent.Created<InactivePersonDto> implements InactivePersonEvent {
        public Created(final InactivePersonDto person) {
            super(person);
        }
    }

}
