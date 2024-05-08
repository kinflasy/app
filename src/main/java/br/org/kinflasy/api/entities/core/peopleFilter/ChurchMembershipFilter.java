package br.org.kinflasy.api.entities.core.peoplefilter;

import java.util.function.Function;

import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.Person;
import br.org.kinflasy.api.entities.core.church.Church;
import br.org.kinflasy.api.utils.enums.core.church.membership.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Table(name = "church_membership_people_filters", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "church_id", "status" })
})
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class ChurchMembershipFilter extends PeopleFilter {

    @ManyToOne
    @JoinColumn(name = "church_id", nullable = false)
    private @NonNull Church church;

    @Enumerated
    @Column(name = "status")
    private @NonNull Status status;

    @Override
    public @NonNull Function<Person, Boolean> getFilter() {
        return (person -> person.getMemberships().stream()
                .anyMatch(membership -> membership.getUnit().getChurch().equals(church)
                        && membership.getStatus() == status));
    }

    @Override
    public @NonNull String toString() {
        return "is " + status.toString() + " of the church " + church.getName() + " (#" + church.getId() + ")";
    }

}
