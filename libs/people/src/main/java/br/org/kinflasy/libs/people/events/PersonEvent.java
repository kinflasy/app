package br.org.kinflasy.libs.people.events;

import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.Data;

public interface PersonEvent<D extends PersonDto> {

    public D getPerson();

    @Data
    public static class Created<D extends PersonDto> implements PersonEvent<D> {
        private final D person;
    }

}
