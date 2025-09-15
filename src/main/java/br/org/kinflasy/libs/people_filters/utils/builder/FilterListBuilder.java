package br.org.kinflasy.libs.people_filters.utils.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import br.org.kinflasy.apis.people_filters.entities.ChurchMembershipFilter;
import br.org.kinflasy.apis.people_filters.entities.DepartmentIntegrationFilter;
import br.org.kinflasy.apis.people_filters.entities.IdentityFilter;
import br.org.kinflasy.apis.people_filters.entities.NegativeFilter;
import br.org.kinflasy.apis.people_filters.entities.OrGroupPeopleFilter;
import br.org.kinflasy.apis.people_filters.entities.PeopleFilter;
import br.org.kinflasy.apis.people_filters.entities.StaticPeopleFilter;
import br.org.kinflasy.apis.people_filters.entities.UnitMembershipFilter;
import br.org.kinflasy.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;

public class FilterListBuilder {

    /**
     * Filter list
     */
    private final List<PeopleFilter> filters = new ArrayList<>();

    /**
     * Package restricted constructor
     */
    FilterListBuilder() {
    }

    public FilterListBuilder not(final Function<PeopleFilterBuilder, ValidPeopleFilterBuilder> filter) {
        final var not = new NegativeFilter(filter.apply(PeopleFilterBuilder.thePerson()).filter);
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
        filters.add(new IdentityFilter(person.getId()));
        return this;
    }

    /**
     * Has a characteristic
     * 
     * @param characteristic
     * @return this
     */
    public FilterListBuilder is(final PersonCharacteristic characteristic) {
        filters.add(new StaticPeopleFilter(characteristic));
        return this;
    }

    /**
     * Is a member of a church
     * 
     * @param churchId
     * @param status
     * @return this
     */
    public FilterListBuilder isMemberOfChurch(final UUID churchId, final Affiliation... status) {
        final var all = new OrGroupPeopleFilter()
                .setFilters(List.of(status).stream()
                        .distinct()
                        .map(stt -> (PeopleFilter) new ChurchMembershipFilter(churchId, stt))
                        .toList());

        filters.add(all);
        return this;
    }

    /**
     * Is a member of a unit
     * 
     * @param unitId
     * @param status
     * @return this
     */
    public FilterListBuilder isMemberOfUnit(final UUID unitId, final Affiliation... status) {
        final var all = new OrGroupPeopleFilter()
                .setFilters(List.of(status).stream()
                        .distinct()
                        .map(stt -> (PeopleFilter) new UnitMembershipFilter(unitId, stt))
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
        final var all = new OrGroupPeopleFilter()
                .setFilters(List.of(integrationTypes)
                        .stream()
                        .distinct()
                        .map(type -> (PeopleFilter) new DepartmentIntegrationFilter(departmentId, type))
                        .toList());

        filters.add(all);
        return this;
    }

    /**
     * Get sequence of filters as a list
     * 
     * @return
     */
    public List<PeopleFilter> getFiltersList() {
        return filters;
    }

}
