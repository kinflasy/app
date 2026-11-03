package br.org.kinflasy.libs.people.ids;

import java.util.UUID;

import br.org.kinflasy.libs.lib_utils.AbstractId;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PersonId extends AbstractId {

    public PersonId(final UUID value) {
        super(value);
    }

}
