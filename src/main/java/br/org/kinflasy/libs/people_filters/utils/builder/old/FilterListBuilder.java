package br.org.kinflasy.libs.people_filters.utils.builder.old;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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

public class FilterListBuilder {

    /**
     * Filter list
     */
    private final List<StoredCondition> filters = new ArrayList<>();

    /**
     * Package restricted constructor
     */
    FilterListBuilder() {
    }

    public FilterListBuilder not(final Function<PeopleFilterBuilder, ValidPeopleFilterBuilder> filter) {
        final var not = new StoredNegativeCondition(filter.apply(PeopleFilterBuilder.thePerson()).filter);
        filters.add(not);

        return this;
    }

    /**
     * Is someone specific
     * 
     * @param person
     * @return this
     */
    public FilterListBuilder is(final PersonDto person) {
        filters.add(new StoredIdentityCondition(person.getId()));
        return this;
    }

    /**
     * Has a characteristic
     * 
     * @param characteristic
     * @return this
     */
    public FilterListBuilder is(final PersonCharacteristic characteristic) {
        filters.add(new StoredCharacteristicCondition(characteristic));
        return this;
    }

    /**
     * Is a member of a church
     * 
     * @param churchId
     * @param affiliation
     * @return this
     */
    public FilterListBuilder isMemberOfChurch(final UUID churchId, final Affiliation... affiliation) {
        final var all = new StoredOrConditionGroup()
                .setFilters(List.of(affiliation).stream()
                        .distinct()
                        .map(stt -> (StoredCondition) new StoredChurchMembershipCondition(churchId, stt))
                        .toList());

        filters.add(all);
        return this;
    }

    /**
     * Is a member of a unit
     * 
     * @param unitId
     * @param affiliation
     * @return this
     */
    public FilterListBuilder isMemberOfUnit(final UUID unitId, final Affiliation... affiliation) {
        final var all = new StoredOrConditionGroup()
                .setFilters(List.of(affiliation).stream()
                        .distinct()
                        .map(stt -> (StoredCondition) new StoredUnitMembershipCondition(unitId, stt))
                        .toList());

        filters.add(all);
        return this;
    }

    /**
     * Is a integrant of a department
     * 
     * @param departmentId
     * @param integrationTypes
     * @return this
     */
    public FilterListBuilder isIntegrantOfDepartment(final UUID departmentId,
            final IntegrationType... integrationTypes) {
        final var all = new StoredOrConditionGroup()
                .setFilters(List.of(integrationTypes)
                        .stream()
                        .distinct()
                        .map(type -> (StoredCondition) new StoredDepartmentIntegrationCondition(departmentId, type))
                        .toList());

        filters.add(all);
        return this;
    }

    /**
     * Get sequence of filters as a list
     * 
     * @return
     */
    public List<StoredCondition> getFiltersList() {
        return filters;
    }

}
