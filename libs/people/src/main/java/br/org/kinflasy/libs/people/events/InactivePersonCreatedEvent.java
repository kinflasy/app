package br.org.kinflasy.libs.people.events;

import br.org.kinflasy.libs.people.dto.InactivePersonDto;

public class InactivePersonCreatedEvent extends PersonCreatedEvent<InactivePersonDto> {

    public InactivePersonCreatedEvent(final InactivePersonDto person) {
        super(person);
    }

}
