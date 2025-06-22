package br.org.kinflasy.api.apis.churches.entities.membership;

import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.apis.churches.entities.Unit;
import br.org.kinflasy.api.apis.people.entities.Person;
import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.libs.churches.enums.membership.Affiliation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "memberships", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "unit_id", "person_id" })
})
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Membership extends AbstractAuditable<User, UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated
    @Column(nullable = false)
    private Affiliation status;

    @ManyToOne(optional = false)
    private Unit unit;

    @ManyToOne(optional = false)
    private Person person;

}
