package br.org.kinflasy.apis.people_filters.entities;

import java.util.UUID;
import java.util.function.Predicate;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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

    private UUID churchId;

    @Column
    @Enumerated
    private Affiliation status;

    @Override
    public Predicate<PersonDto> getPredicate() {
        // TODO implementar filtro
        return p -> false;
    }

}
