package br.org.kinflasy.libs.people_filters.conditions;

import java.util.UUID;

import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IdentityCondition extends Condition {

    private final UUID personId;

    @Override
    public boolean test(final PersonDto person) {
        return person.getId().equals(personId);
    }

}
