package br.org.kinflasy.apis.people_filters.predicates.business;

import org.springframework.stereotype.Component;

import br.org.kinflasy.apis.people_filters.clients.ChurchClient;
import br.org.kinflasy.apis.people_filters.predicates.structure.ConditionPredicate;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.conditions.business.ChurchMembershipCondition;
import br.org.kinflasy.libs.people_filters.conditions.business.UnitMembershipCondition;
import lombok.Data;

@Data
@Component
public class ChurchMembershipPredicate implements ConditionPredicate<ChurchMembershipCondition> {

    private final ChurchClient client;
    private final UnitMembershipPredicate unitMembershipPredicate;

    @Override
    public boolean test(final ChurchMembershipCondition condition, final PersonDto person) {
        // Listar todas as unidades de uma Igreja
        final var units = client.listUnits(condition.getChurchId());

        // Testar se uma delas possui a membresia em questão
        return units.stream()
                .anyMatch(unit -> {
                    final var unitMembershipCondition = new UnitMembershipCondition(unit.getId(),
                            condition.getAffiliation());
                    return unitMembershipPredicate.test(unitMembershipCondition, person);
                });
    }

}
