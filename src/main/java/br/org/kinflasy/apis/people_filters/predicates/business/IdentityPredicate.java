package br.org.kinflasy.apis.people_filters.predicates.business;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Component
public class IdentityPredicate extends ConditionPredicate {

    private final UUID personId;

    @Override
    public boolean test(final PersonDto person) {
        return person.getId().equals(personId);
    }

}
