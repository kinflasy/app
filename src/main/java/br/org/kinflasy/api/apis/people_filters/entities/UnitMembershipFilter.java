package br.org.kinflasy.api.apis.people_filters.entities;

import java.util.function.Predicate;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.apis.churches.entities.Unit;
import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.libs.churches.enums.membership.Affiliation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit_membership_people_filters", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "unit_id", "status" })
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UnitMembershipFilter extends PeopleFilter {

    @ManyToOne(optional = false)
    private Unit unit;

    @Enumerated
    @Column
    private Affiliation status;

    @Override
    public Predicate<Person> getPredicate() {
        return (person -> person.getMemberships().stream()
                .anyMatch(membership -> membership.getUnit().equals(unit)
                        && membership.getStatus() == status));
    }

    @Override
    public @NonNull String toString() {
        return "is " + status.toString() + " of the unit " + unit.getName() + " (#" + unit.getId() + ")";
    }

}
