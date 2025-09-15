package br.org.kinflasy.libs.people_filters.utils.builder;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import br.org.kinflasy.apis.people_filters.entities.AndConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.CharacteristicCondition;
import br.org.kinflasy.apis.people_filters.entities.ChurchMembershipCondition;
import br.org.kinflasy.apis.people_filters.entities.Condition;
import br.org.kinflasy.apis.people_filters.entities.DepartmentIntegrationCondition;
import br.org.kinflasy.apis.people_filters.entities.IdentityCondition;
import br.org.kinflasy.apis.people_filters.entities.NegativeCondition;
import br.org.kinflasy.apis.people_filters.entities.OrConditionGroup;
import br.org.kinflasy.apis.people_filters.entities.UnitMembershipCondition;
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
        final var not = new NegativeCondition(filter.apply(this).filter);
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
        final var and = new AndConditionGroup().setFilters(listed.getFiltersList());
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
        final var or = new OrConditionGroup().setFilters(listed.getFiltersList());
        return new SinglyPeopleFilterBuilder(or);
    }

    /**
     * Is someone specific
     * 
     * @param person
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder is(final PersonDto person) {
        return new SinglyPeopleFilterBuilder(new IdentityCondition(person.getId()));
    }

    /**
     * Has a characteristic
     * 
     * @param characteristic
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder is(final PersonCharacteristic characteristic) {
        return new SinglyPeopleFilterBuilder(new CharacteristicCondition(characteristic));
    }

    /**
     * Is a member of a church
     * 
     * @param churchId
     * @param affiliation
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder isMemberOfChurch(final UUID churchId, final Affiliation... affiliation) {
        final var all = new OrConditionGroup()
                .setFilters(List.of(affiliation).stream()
                        .distinct()
                        .map(stt -> (Condition) new ChurchMembershipCondition(churchId, stt))
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
        final var all = new OrConditionGroup()
                .setFilters(List.of(affiliation).stream()
                        .distinct()
                        .map(stt -> (Condition) new UnitMembershipCondition(unitId, stt))
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
        final var all = new OrConditionGroup()
                .setFilters(List.of(integrationTypes).stream()
                        .distinct()
                        .map(type -> (Condition) new DepartmentIntegrationCondition(departmentId, type))
                        .toList());

        return new SinglyPeopleFilterBuilder(all);
    }

}
