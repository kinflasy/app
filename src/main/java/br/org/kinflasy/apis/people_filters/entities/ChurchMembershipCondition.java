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
@Table(name = "conditions_church_membership", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "church_id", "status" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChurchMembershipCondition extends Condition {

    @Column(nullable = false)
    private UUID churchId;

    @Column
    @Enumerated
    private Affiliation affiliation;

    @Override
    public boolean test(final PersonDto person) {
        // TODO implementar filtro
        return false;
    }

}
