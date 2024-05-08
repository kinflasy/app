package br.org.kinflasy.api.entities.core.peoplefilter.builder;

import java.util.List;
import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.entities.core.church.Unit;
import br.org.kinflasy.api.entities.core.church.department.Department;
import br.org.kinflasy.api.entities.core.peoplefilter.AndGroupPeopleFilter;
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

public class PeopleFilterBuilder {

    /**
     * Initialize the declaration of a filter
     * 
     * @return
     */
    public static @NonNull PeopleFilterBuilder thePerson() {
        return new PeopleFilterBuilder();
    }

    /**
     * Aggregate with AND
     * 
     * @param list
     * @return SinglyPeopleFilterBuilder
     */
    public @NonNull SinglyPeopleFilterBuilder matchesAll(
            final @NonNull Function<FilterListBuilder, FilterListBuilder> list) {
        final var listed = list.apply(new FilterListBuilder());
        final var and = new AndGroupPeopleFilter(listed.getFiltersList());
        return new SinglyPeopleFilterBuilder(and);
    }

    /**
     * Aggregate with OR
     * 
     * @param list
     * @return SinglyPeopleFilterBuilder
     */
    public @NonNull SinglyPeopleFilterBuilder matchesOneOf(
            final @NonNull Function<FilterListBuilder, FilterListBuilder> list) {
        final var listed = list.apply(new FilterListBuilder());
        final var or = new OrGroupPeopleFilter(listed.getFiltersList());
        return new SinglyPeopleFilterBuilder(or);
    }

    /**
     * Is someone specific
     * 
     * @param person
     * @return SinglyPeopleFilterBuilder
     */
    public @NonNull SinglyPeopleFilterBuilder is(final @NonNull Person person) {
        return new SinglyPeopleFilterBuilder(new IdentityFilter(person));
    }

    /**
     * Has a characteristic
     * 
     * @param characteristic
     * @return SinglyPeopleFilterBuilder
     */
    public @NonNull SinglyPeopleFilterBuilder is(final @NonNull PersonCharacteristic characteristic) {
        return new SinglyPeopleFilterBuilder(new StaticPeopleFilter(characteristic));
    }

    /**
     * Is a member of a church
     * 
     * @param church
     * @param status
     * @return SinglyPeopleFilterBuilder
     */
    public @NonNull SinglyPeopleFilterBuilder isMemberOf(final @NonNull Church church,
            final @NonNull Status... status) {
        final var all = new OrGroupPeopleFilter(
                List.of(status)
                        .stream()
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
    public @NonNull SinglyPeopleFilterBuilder isMemberOf(final @NonNull Unit unit, final @NonNull Status... status) {
        final var all = new OrGroupPeopleFilter(
                List.of(status)
                        .stream()
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
    public @NonNull SinglyPeopleFilterBuilder isIntegrantOf(final @NonNull Department department,
            final @NonNull IntegrationType... integrationTypes) {
        final var all = new OrGroupPeopleFilter(
                List.of(integrationTypes)
                        .stream()
                        .distinct()
                        .map(type -> (PeopleFilter) new DepartmentIntegrationFilter(department, type))
                        .toList());

        return new SinglyPeopleFilterBuilder(all);
    }

    public static PeopleFilter test() {
        final var personX = new User();
        final var churchX = new Church();
        final var unitX = new Unit();
        final var departmentX = new Department();

        final var builder = PeopleFilterBuilder
                .thePerson()
                .isMemberOf(unitX, Status.CONGREGATED, Status.MEMBER, Status.VISITOR)
                .andMatchesAll(
                        person -> person
                                .is(personX)
                                .is(PersonCharacteristic.ADULT)
                                .isMemberOf(churchX, Status.MEMBER))
                .allThisOrMatchesAll(list -> list
                        .isMemberOf(unitX, Status.CONGREGATED)
                        .isIntegrantOf(departmentX, IntegrationType.LEADER));

        System.out.println(builder);

        final var result = builder.build();

        System.out.println(result);

        return result;
    }

}
