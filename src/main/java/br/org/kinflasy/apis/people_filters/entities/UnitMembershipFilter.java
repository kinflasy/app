package br.org.kinflasy.apis.people_filters.entities;

import java.util.UUID;

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
@Table(name = "unit_membership_people_filters", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "unit_id", "status" })
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UnitMembershipFilter extends PeopleFilter {

    @Column(nullable = false)
    private UUID unitId;

    @Column
    @Enumerated
    private Affiliation status;

    @Override
    public boolean test(final PersonDto person) {
        // TODO implementar filtro
        return false;
    }

}
