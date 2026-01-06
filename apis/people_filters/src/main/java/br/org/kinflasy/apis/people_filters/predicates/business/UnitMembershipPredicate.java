package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.clients.UnitClient;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.UnitMembershipCondition;
import feign.FeignException;
import lombok.Data;

@Data
@Component
public class UnitMembershipPredicate implements ConditionPredicate<UnitMembershipCondition> {

    private final UnitClient client;

    @Override
    public boolean test(final UnitMembershipCondition condition, final PersonDto person) {
        try {
            // Obter os dados de membresia dessa pessoa nessa unidade
            final var membership = client.findActiveMembership(condition.getUnitId(), person.getId());

            // Pular checagem de afiliação, caso a condição não exija
            if (condition.getAffiliation() == null) {
                return true;
            }

            // Verificar se a afiliação condiz
            return membership.getAffiliation().includes(condition.getAffiliation());
        } catch (final FeignException.NotFound e) {
            return false;
        }
    }

}
