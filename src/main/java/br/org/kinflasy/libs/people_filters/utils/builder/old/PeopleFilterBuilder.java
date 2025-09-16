package br.org.kinflasy.libs.people_filters.utils.builder.old;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import br.org.kinflasy.apis.people_filters.entities.StoredAndConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredCharacteristicCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredChurchMembershipCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredDepartmentIntegrationCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredIdentityCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredNegativeCondition;
import br.org.kinflasy.apis.people_filters.entities.StoredOrConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.StoredUnitMembershipCondition;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;

public class PeopleFilterBuilder {

    /**
     * Initialize the declaration of a filter
     * 
     * @return
     */
    public static PeopleFilterBuilder thePerson() {
        return new PeopleFilterBuilder();
    }

    public SinglyPeopleFilterBuilder not(final Function<PeopleFilterBuilder, ValidPeopleFilterBuilder> filter) {
        final var not = new StoredNegativeCondition(filter.apply(this).filter);
        return new SinglyPeopleFilterBuilder(not);
    }

    /**
     * Aggregate with AND
     * 
     * @param list
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder matchesAll(final UnaryOperator<FilterListBuilder> list) {
        final var listed = list.apply(new FilterListBuilder());
        final var and = new StoredAndConditionGroup().setConditions(listed.getConditionsList());
        return new SinglyPeopleFilterBuilder(and);
    }

    /**
     * Aggregate with OR
     * 
     * @param list
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder matchesOneOf(final UnaryOperator<FilterListBuilder> list) {
        final var listed = list.apply(new FilterListBuilder());
        final var or = new StoredOrConditionGroup().setConditions(listed.getConditionsList());
        return new SinglyPeopleFilterBuilder(or);
    }

    /**
     * Is someone specific
     * 
     * @param person
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder is(final PersonDto person) {
        return new SinglyPeopleFilterBuilder(new StoredIdentityCondition(person.getId()));
    }

    /**
     * Has a characteristic
     * 
     * @param characteristic
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder is(final PersonCharacteristic characteristic) {
        return new SinglyPeopleFilterBuilder(new StoredCharacteristicCondition(characteristic));
    }

    /**
     * Is a member of a church
     * 
     * @param churchId
     * @param affiliation
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder isMemberOfChurch(final UUID churchId, final Affiliation... affiliation) {
        final var all = new StoredOrConditionGroup()
                .setConditions(List.of(affiliation).stream()
                        .distinct()
                        .map(stt -> (StoredCondition) new StoredChurchMembershipCondition(churchId, stt))
                        .toList());

        return new SinglyPeopleFilterBuilder(all);
    }

    /**
     * Is a member of a unit
     * 
     * @param unitId
     * @param affiliation
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder isMemberOfUnit(final UUID unitId, final Affiliation... affiliation) {
        final var all = new StoredOrConditionGroup()
                .setConditions(List.of(affiliation).stream()
                        .distinct()
                        .map(stt -> (StoredCondition) new StoredUnitMembershipCondition(unitId, stt))
                        .toList());

        return new SinglyPeopleFilterBuilder(all);
    }

    /**
     * Is a integrant of a department
     * 
     * @param departmentId
     * @param integrationTypes
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder isIntegrantOfDepartment(final UUID departmentId,
            final IntegrationType... integrationTypes) {
        final var all = new StoredOrConditionGroup()
                .setConditions(List.of(integrationTypes).stream()
                        .distinct()
                        .map(type -> (StoredCondition) new StoredDepartmentIntegrationCondition(departmentId, type))
                        .toList());

        return new SinglyPeopleFilterBuilder(all);
    }

}
