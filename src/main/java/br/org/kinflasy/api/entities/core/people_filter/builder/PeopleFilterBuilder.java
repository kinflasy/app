package br.org.kinflasy.api.entities.core.people_filter.builder;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.people_filter.AndGroupPeopleFilter;
import br.org.kinflasy.api.entities.core.people_filter.ChurchMembershipFilter;
import br.org.kinflasy.api.entities.core.people_filter.DepartmentIntegrationFilter;
import br.org.kinflasy.api.entities.core.people_filter.IdentityFilter;
import br.org.kinflasy.api.entities.core.people_filter.NegativeFilter;
import br.org.kinflasy.api.entities.core.people_filter.OrGroupPeopleFilter;
import br.org.kinflasy.api.entities.core.people_filter.PeopleFilter;
import br.org.kinflasy.api.entities.core.people_filter.StaticPeopleFilter;
import br.org.kinflasy.api.entities.core.people_filter.UnitMembershipFilter;
import br.org.kinflasy.api.utils.enums.core.PersonCharacteristic;
import br.org.kinflasy.api.utils.enums.core.church.department.IntegrationType;
import br.org.kinflasy.api.utils.enums.core.church.membership.Status;

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
        final var not = new NegativeFilter(filter.apply(this).filter);
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
        final var and = new AndGroupPeopleFilter().setFilters(listed.getFiltersList());
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
        final var or = new OrGroupPeopleFilter().setFilters(listed.getFiltersList());
        return new SinglyPeopleFilterBuilder(or);
    }

    /**
     * Is someone specific
     * 
     * @param person
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder is(final Person person) {
        return new SinglyPeopleFilterBuilder(new IdentityFilter(person));
    }

    /**
     * Has a characteristic
     * 
     * @param characteristic
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder is(final PersonCharacteristic characteristic) {
        return new SinglyPeopleFilterBuilder(new StaticPeopleFilter(characteristic));
    }

    /**
     * Is a member of a church
     * 
     * @param church
     * @param status
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder isMemberOf(final Church church,
            final Status... status) {
        final var all = new OrGroupPeopleFilter()
                .setFilters(List.of(status).stream()
                        .distinct()
                        .map(stt -> (PeopleFilter) new ChurchMembershipFilter(church, stt))
                        .toList());

        return new SinglyPeopleFilterBuilder(all);
    }

    /**
     * Is a member of a unit
     * 
     * @param unit
     * @param status
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder isMemberOf(final Unit unit, final Status... status) {
        final var all = new OrGroupPeopleFilter()
                .setFilters(List.of(status).stream()
                        .distinct()
                        .map(stt -> (PeopleFilter) new UnitMembershipFilter(unit, stt))
                        .toList());

        return new SinglyPeopleFilterBuilder(all);
    }

    /**
     * Is a integrant of a department
     * 
     * @param department
     * @param integrationTypes
     * @return SinglyPeopleFilterBuilder
     */
    public SinglyPeopleFilterBuilder isIntegrantOf(final Department department,
            final IntegrationType... integrationTypes) {
        final var all = new OrGroupPeopleFilter()
                .setFilters(List.of(integrationTypes).stream()
                        .distinct()
                        .map(type -> (PeopleFilter) new DepartmentIntegrationFilter(department, type))
                        .toList());

        return new SinglyPeopleFilterBuilder(all);
    }

}
