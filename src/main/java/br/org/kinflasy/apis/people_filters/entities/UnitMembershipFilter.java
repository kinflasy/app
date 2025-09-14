package br.org.kinflasy.apis.people_filters.entities;

import java.util.UUID;
import java.util.function.Predicate;

import br.org.kinflasy.libs.churches.enums.membership.Affiliation;
import br.org.kinflasy.libs.people.dto.PersonDto;
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
    private UUID unitId;

    @Enumerated
    @Column
    private Affiliation status;

    @Override
    public Predicate<PersonDto> getPredicate() {
        // TODO implementar filtro
        return p -> false;
    }

}
