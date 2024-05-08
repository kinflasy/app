package br.org.kinflasy.api.entities.core.peoplefilter.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.peoplefilter.ChurchMembershipFilter;
import br.org.kinflasy.api.entities.core.peoplefilter.DepartmentIntegrationFilter;
import br.org.kinflasy.api.entities.core.peoplefilter.IdentityFilter;
import br.org.kinflasy.api.entities.core.peoplefilter.OrGroupPeopleFilter;
import br.org.kinflasy.api.entities.core.peoplefilter.PeopleFilter;
import br.org.kinflasy.api.entities.core.peoplefilter.StaticPeopleFilter;
import br.org.kinflasy.api.entities.core.peoplefilter.UnitMembershipFilter;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;
import br.org.kinflasy.api.utils.enums.core.church.department.IntegrationType;
import br.org.kinflasy.api.utils.enums.core.church.membership.Status;

public class FilterListBuilder {

    /**
     * Filter list
     */
    private final @NonNull List<PeopleFilter> filters = new ArrayList<>();

    /**
     * Package restricted constructor
     */
    FilterListBuilder() {
    }

    /**
     * Is someone specific
     * 
     * @param person
     * @return this
     */
    public @NonNull FilterListBuilder is(final @NonNull Person person) {
        filters.add(new IdentityFilter(person));
        return this;
    }

    /**
     * Has a characteristic
     * 
     * @param characteristic
     * @return this
     */
    public @NonNull FilterListBuilder is(final @NonNull PersonCharacteristic characteristic) {
        filters.add(new StaticPeopleFilter(characteristic));
        return this;
    }

    /**
     * Is a member of a church
     * 
     * @param church
     * @param status
     * @return this
     */
    public @NonNull FilterListBuilder isMemberOf(final @NonNull Church church, final @NonNull Status... status) {
        final var all = new OrGroupPeopleFilter(
                List.of(status)
                        .stream()
                        .distinct()
                        .map(stt -> (PeopleFilter) new ChurchMembershipFilter(church, stt))
                        .toList());

        filters.add(all);
        return this;
    }

    /**
     * Is a member of a unit
     * 
     * @param unit
     * @param status
     * @return this
     */
    public @NonNull FilterListBuilder isMemberOf(final @NonNull Unit unit, final @NonNull Status... status) {
        final var all = new OrGroupPeopleFilter(
                List.of(status)
                        .stream()
                        .distinct()
                        .map(stt -> (PeopleFilter) new UnitMembershipFilter(unit, stt))
                        .toList());

        filters.add(all);
        return this;
    }

    /**
     * Is a integrant of a department
     * 
     * @param department
     * @param integrationTypes
     * @return this
     */
    public @NonNull FilterListBuilder isIntegrantOf(final @NonNull Department department,
            final @NonNull IntegrationType... integrationTypes) {
        final var all = new OrGroupPeopleFilter(
                List.of(integrationTypes)
                        .stream()
                        .distinct()
                        .map(type -> (PeopleFilter) new DepartmentIntegrationFilter(department, type))
                        .toList());

        filters.add(all);
        return this;
    }

    /**
     * Get sequence of filters as a list
     * 
     * @return
     */
    public @NonNull List<PeopleFilter> getFiltersList() {
        return filters;
    }

}
