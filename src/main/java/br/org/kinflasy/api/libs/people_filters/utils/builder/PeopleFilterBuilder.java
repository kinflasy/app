package br.org.kinflasy.api.libs.people_filters.utils.builder;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import br.org.kinflasy.api.apis.churches.entities.Church;
import br.org.kinflasy.api.apis.churches.entities.Unit;
import br.org.kinflasy.api.apis.churches.entities.department.Department;
import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.apis.people_filters.entities.AndGroupPeopleFilter;
import br.org.kinflasy.api.apis.people_filters.entities.ChurchMembershipFilter;
import br.org.kinflasy.api.apis.people_filters.entities.DepartmentIntegrationFilter;
import br.org.kinflasy.api.apis.people_filters.entities.IdentityFilter;
import br.org.kinflasy.api.apis.people_filters.entities.NegativeFilter;
import br.org.kinflasy.api.apis.people_filters.entities.OrGroupPeopleFilter;
import br.org.kinflasy.api.apis.people_filters.entities.PeopleFilter;
import br.org.kinflasy.api.apis.people_filters.entities.StaticPeopleFilter;
import br.org.kinflasy.api.apis.people_filters.entities.UnitMembershipFilter;
import br.org.kinflasy.api.libs.churches.enums.department.IntegrationType;
import br.org.kinflasy.api.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.api.libs.people_filters.enums.PersonCharacteristic;

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
            final Affiliation... status) {
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
    public SinglyPeopleFilterBuilder isMemberOf(final Unit unit, final Affiliation... status) {
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
