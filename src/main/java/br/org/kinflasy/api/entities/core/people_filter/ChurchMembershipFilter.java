package br.org.kinflasy.api.entities.core.people_filter;

import java.util.function.Predicate;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.utils.enums.core.church.membership.Affiliation;
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
@Table(name = "church_membership_people_filters", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "church_id", "status" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChurchMembershipFilter extends PeopleFilter {

    @ManyToOne(optional = false)
    private Church church;

    @Enumerated
    @Column
    private Affiliation status;

    @Override
    public Predicate<Person> getPredicate() {
        return (person -> person.getMemberships().stream()
                .anyMatch(membership -> membership.getUnit().getChurch().equals(church)
                        && membership.getStatus() == status));
    }

    @Override
    public @NonNull String toString() {
        return "is " + status.toString() + " of the church " + church.getName() + " (#" + church.getId() + ")";
    }

}
