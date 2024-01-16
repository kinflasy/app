package br.org.kinflasy.api.entities.core.peopleFilter;

import java.util.function.Function;

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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "church_membership_people_filters", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"church_id", "status"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class ChurchMembershipFilter extends PeopleFilter {

    @ManyToOne
    @JoinColumn(name = "church_id", nullable = false)
    private Church church;

    @Enumerated
    @Column(name = "status")
    private Status status;

    @Override
    public Function<Person, Boolean> getFilter() {
        // TODO: escrever regra de negócio (usar repository/service)
        throw new UnsupportedOperationException("Unimplemented method 'getFilter'");
    }

}
